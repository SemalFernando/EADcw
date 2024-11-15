package eadcw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementSystem extends JFrame {

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        setVisible(true);
    }

    
    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }
}
