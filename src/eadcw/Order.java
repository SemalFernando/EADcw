/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eadcw;

import java.awt.Color;
import java.io.InputStream;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Owner
 */
public class Order extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    DefaultTableModel model;

    /**
     * Creates new form Order
     */
    public Order() {
        initComponents();
        connect();
        loadCustomerOrders();
        fetchOrders();
        //calculateTotal();
    }

    private void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/inventory_management", "root", "Semali33g8hby");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed");
        }
    }

    private void loadCustomerOrders() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customerID FROM customer");
            cmbCustomer.removeAllItems();
            while (rs.next()) {
                cmbCustomer.addItem(rs.getString("customerID"));
            }

            rs = stmt.executeQuery("SELECT productID FROM product");
            cmbProduct.removeAllItems();
            while (rs.next()) {
                cmbProduct.addItem(rs.getString("productID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load Customer and Product data");
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // or your desired format
        txtDate.setText(formatter.format(date));
    }

    private void fetchOrders() {
        try {
            // SQL query to explicitly select required columns
            String query = "SELECT CustomerID, ProductID, Quantity, TotalValue, OrderDate FROM orders";

            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            // Get the table model and clear previous rows
            model = (DefaultTableModel) orderTable.getModel();
            model.setRowCount(0);  // Clear existing rows

            // Check if the query is returning results
            boolean dataAvailable = false;
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("CustomerID"),
                    rs.getString("ProductID"),
                    rs.getString("Quantity"),
                    rs.getDouble("TotalValue"),
                    rs.getString("OrderDate")
                });
                dataAvailable = true;
            }

            // Check if no data was found
            if (!dataAvailable) {
                JOptionPane.showMessageDialog(this, "No orders found.");
            }

        } catch (SQLException ex) {
            // Print the exception message for debugging
            System.out.println("Error fetching orders: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to retrieve data from Orders table");
        }
    }

    private void addOrder() {
        String customerID = (String) cmbCustomer.getSelectedItem();
        String productID = (String) cmbProduct.getSelectedItem();
        String quantityStr = txtQty.getText();
        String date = txtDate.getText();

        // Validate input fields
        if (customerID == null || productID == null || quantityStr.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            // Convert quantity to integer and validate it
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive number.");
                return;
            }

            // Fetch product price from the database
            pst = con.prepareStatement("SELECT UnitPrice FROM product WHERE productID = ?");
            pst.setString(1, productID);
            ResultSet rs = pst.executeQuery();
            double productPrice = 0;
            if (rs.next()) {
                productPrice = rs.getDouble("UnitPrice");
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
                return;
            }

            // Calculate the total value
            double totalValue = quantity * productPrice;

            // Insert order into the orders table
            pst = con.prepareStatement("INSERT INTO orders (CustomerID, ProductID, Quantity, TotalValue, OrderDate) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, customerID);
            pst.setString(2, productID);
            pst.setInt(3, quantity);  // Quantity as int
            pst.setDouble(4, totalValue);  // Order value as double
            pst.setString(5, date);  // Order date as String (ensure it's in the correct format)

            // Execute the update
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order Added Successfully.");

            // Refresh the orders table
            fetchOrders();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to add order: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        }
    }

    private void updateOrder() {
        String customerID = (String) cmbCustomer.getSelectedItem();
        String productID = (String) cmbProduct.getSelectedItem();
        String quantityStr = txtQty.getText();
        String date = txtDate.getText();

        // Input Validation
        if (customerID == null || productID == null || quantityStr.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            // Convert quantity to integer and validate it
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive number.");
                return;
            }

            // Fetch product price from the database
            pst = con.prepareStatement("SELECT UnitPrice FROM product WHERE productID = ?");
            pst.setString(1, productID);
            ResultSet rs = pst.executeQuery();
            float productPrice = 0;
            if (rs.next()) {
                productPrice = rs.getFloat("UnitPrice");
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
                return;
            }

            // Calculate the total value
            float totalValue = quantity * productPrice;

            // Update order in the orders table
            pst = con.prepareStatement("UPDATE orders SET Quantity = ?, TotalValue = ?, OrderDate = ? WHERE CustomerID = ? AND ProductID = ?");
            pst.setInt(1, quantity);  // Quantity as integer
            pst.setFloat(2, totalValue);  // TotalValue as double
            pst.setString(3, date);  // OrderDate as string (ensure the correct format)
            pst.setString(4, customerID);
            pst.setString(5, productID);

            // Execute the update
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order Updated");

            // Refresh the orders table
            fetchOrders();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update order: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        }
    }

    private void deleteOrder() {
        String customerID = (String) cmbCustomer.getSelectedItem();
        String productID = (String) cmbProduct.getSelectedItem();
        try {
            pst = con.prepareStatement("DELETE FROM orders WHERE CustomerID=? AND ProductID=?");
            pst.setString(1, customerID);
            pst.setString(2, productID);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order Deleted");
            fetchOrders();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to delete order");
        }
    }

    /*private void calculateTotal() {
        try {
            pst = con.prepareStatement("SELECT SUM(TotalValue) AS TotalValue FROM orders");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtTotal.setText(String.valueOf(rs.getDouble("TotalValue")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to calculate total value");
        }
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbCustomer = new javax.swing.JComboBox<>();
        cmbProduct = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        txtQty = new javax.swing.JTextField();
        btnback = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnrefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnadd = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        setForeground(new java.awt.Color(255, 102, 0));

        cmbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Product ID", "Quantity", "Total Value", "Order Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(orderTable);

        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        btnback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/back-button (1).png"))); // NOI18N
        btnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackActionPerformed(evt);
            }
        });

        jLabel4.setText("Product ID");

        btnrefresh.setBackground(new java.awt.Color(51, 51, 51));
        btnrefresh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnrefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnrefresh.setText("Refresh");
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        jLabel1.setText("Customer ID");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("ORDER DETAILS");

        jLabel2.setText("Quantity");

        btndelete.setBackground(new java.awt.Color(51, 51, 51));
        btndelete.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setBackground(new java.awt.Color(51, 51, 51));
        btnupdate.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnupdate.setForeground(new java.awt.Color(255, 255, 255));
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnadd.setBackground(new java.awt.Color(51, 51, 51));
        btnadd.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnadd.setForeground(new java.awt.Color(255, 255, 255));
        btnadd.setText("Add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        jLabel5.setText("Order Date");

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbProduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnrefresh)
                    .addComponent(jLabel6))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnback)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        String input = txtQty.getText();
        try {
            int quantity = Integer.parseInt(input);
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be negative.");
                txtQty.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            txtQty.setText("");
        }
    }//GEN-LAST:event_txtQtyActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        Dashboard dbForm = new Dashboard();
        dbForm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnbackActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        loadCustomerOrders();
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        deleteOrder();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        updateOrder();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        //btnadd.setBackground(new Color(255, 99, 71));
        addOrder();
    }//GEN-LAST:event_btnaddActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Order().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JComboBox<String> cmbProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable orderTable;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}
