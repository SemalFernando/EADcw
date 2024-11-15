package eadcw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends javax.swing.JFrame {

    public Dashboard() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btncustomer = new javax.swing.JButton();
        btninventory = new javax.swing.JButton();
        btnorders = new javax.swing.JButton();
        btnreports = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btncustomer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btncustomer.setForeground(new java.awt.Color(237, 51, 5));
        btncustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/customer-service (1).png"))); // NOI18N
        btncustomer.setText("Customers");
        btncustomer.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btncustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncustomer.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btncustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncustomerActionPerformed(evt);
            }
        });
        getContentPane().add(btncustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        btninventory.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btninventory.setForeground(new java.awt.Color(237, 51, 5));
        btninventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/inventory (1).png"))); // NOI18N
        btninventory.setText("Inventory");
        btninventory.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btninventory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btninventory.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btninventory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btninventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninventoryActionPerformed(evt);
            }
        });
        getContentPane().add(btninventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 110, -1, -1));

        btnorders.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnorders.setForeground(new java.awt.Color(237, 51, 5));
        btnorders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/order (1).png"))); // NOI18N
        btnorders.setText("Orders");
        btnorders.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnorders.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnorders.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnorders.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnorders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnordersActionPerformed(evt);
            }
        });
        getContentPane().add(btnorders, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 110, -1, -1));

        btnreports.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnreports.setForeground(new java.awt.Color(237, 51, 5));
        btnreports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/report (1).png"))); // NOI18N
        btnreports.setText("Reports");
        btnreports.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnreports.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnreports.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnreports.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnreports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportsActionPerformed(evt);
            }
        });
        getContentPane().add(btnreports, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 110, -1, -1));

        btnLogOut.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(237, 51, 5));
        btnLogOut.setText("Log out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 90, 32));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 34)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DASHBOARD");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, -1, -1));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eadcw/6.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncustomerActionPerformed

        btncustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                Customer customerForm = new Customer();
                customerForm.setVisible(true);
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_btncustomerActionPerformed

    private void btninventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninventoryActionPerformed
        dispose();
        Inventory products = new Inventory();
        products.setVisible(true);
    }//GEN-LAST:event_btninventoryActionPerformed

    private void btnordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnordersActionPerformed
        dispose();
        Order order = new Order();
        order.setVisible(true);
    }//GEN-LAST:event_btnordersActionPerformed

    private void btnreportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreportsActionPerformed
        dispose();
        Report report = new Report();
        report.setVisible(true);
    }//GEN-LAST:event_btnreportsActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        dispose();
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_btnLogOutActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btncustomer;
    private javax.swing.JButton btninventory;
    private javax.swing.JButton btnorders;
    private javax.swing.JButton btnreports;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
