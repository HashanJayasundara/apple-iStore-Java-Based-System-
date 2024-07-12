package Controller;

import Model.ProductModel;
import javax.swing.JComboBox;
import java.sql.*;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductController {

    private ProductModel product;

    

    public ProductController(ProductModel product) {
        this.product = product;
    }

    //add product
    public void addProduct() {
        try (Connection conn = DatabaseController.conn(); PreparedStatement pst = conn.prepareStatement("INSERT INTO product VALUES (?,?,?,?,?)")) {
            pst.setString(1, product.getProductId());
            pst.setString(2, product.getProductName());
            pst.setString(3, product.getProductCategory());
            pst.setDouble(4, product.getProductPrice());
            pst.setInt(5, product.getProductQty());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Product added successfully..!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //view data in table
    public static void viewDataInTable(JTable tblStock) {
        try (Statement stm = DatabaseController.conn().createStatement(); ResultSet rs = stm.executeQuery("SELECT * FROM product")) {
            DefaultTableModel table = (DefaultTableModel) tblStock.getModel();
            table.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                table.addRow(v);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //search data
    public static void searchData(JTable tblStock, String id) {
        try (Connection conn = DatabaseController.conn(); PreparedStatement pst = conn.prepareStatement("SELECT * FROM product WHERE ProductID=? OR ProductName=? OR Price=? OR Category=?")) {
            pst.setString(1, id);
            pst.setString(2, id);
            pst.setString(3, id);
            pst.setString(4, id);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) tblStock.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                dt.addRow(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //Updating a product
    public void updateProduct(String productID) {

        try (Connection conn = DatabaseController.conn(); PreparedStatement pst = conn.prepareStatement("UPDATE product SET ProductName=?, Category=?, Price=?, Quantity=? WHERE ProductID='" + productID + "'")) {
            //System.out.println("SQL DONE");
            pst.setString(1, product.getProductName());
            pst.setString(2, product.getProductCategory());
            pst.setString(3, String.valueOf(product.getProductPrice()));
            pst.setString(4, String.valueOf(product.getProductQty()));
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Updated Product : " + productID);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    //Deleting a product
    public static void deleteProduct(String productID) {
        int response = JOptionPane.showConfirmDialog(null, "Are You Sure Want To Delete Product at Product", "Delete Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseController.conn()) {
                conn.setAutoCommit(false);

                try (PreparedStatement pstDelete = conn.prepareStatement("DELETE FROM product WHERE ProductID=?")) {
                    pstDelete.setString(1, productID);
                    pstDelete.executeUpdate();
                }
                conn.commit();
                JOptionPane.showMessageDialog(null, "Product was Deleted");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting record: " + e.getMessage());
            }
        }
    }

    static String id;
    static String pname;
    static String category;
    static int qty, inventory, updatedQty;
    static double price, subtotal;

    public static void selectRow(JTable stock){
        
        int getSelectedRow = stock.getSelectedRow();
        
        if(getSelectedRow == -1){
            JOptionPane.showMessageDialog(null, "Error");
            return;
        }
        
        id = stock.getValueAt(getSelectedRow, 0).toString();
        pname = stock.getValueAt(getSelectedRow, 1).toString();
        category = stock.getValueAt(getSelectedRow, 2).toString();
        inventory = Integer.parseInt(stock.getValueAt(getSelectedRow, 4).toString());
        price = Double.parseDouble(stock.getValueAt(getSelectedRow, 3).toString());
        
    }
    
    public static void addRowToOrderTable(JTable orderTable, JTextField itemqty, JTextField PaybleAmount) {
        try{
        qty = Integer.parseInt(itemqty.getText());
        if (inventory == 0) {
            JOptionPane.showMessageDialog(null, "Item Out of Stock", "Stock Checking", 0);
        } else if (inventory < qty) {
            JOptionPane.showMessageDialog(null, "Item Out of Stock", "Stock Checking", 0);
        } else if(qty <= 0){
            JOptionPane.showMessageDialog(null, "No Value", "Stock Checking", 0);
        }
        else {

            subtotal = qty * price;
            DefaultTableModel orderTableModel = (DefaultTableModel) orderTable.getModel();
            Vector rowData = new Vector();
            rowData.add(id);
            rowData.add(pname);
            rowData.add(category);
            rowData.add(price);
            rowData.add(qty);
            rowData.add(subtotal);
            orderTableModel.addRow(rowData);
            double fullAmount = 0.0;
            for (int i = 0; i < orderTable.getRowCount(); i++) {
                double total = (double) orderTable.getValueAt(i, 5);
                fullAmount += total;
            }
            PaybleAmount.setText(String.valueOf(fullAmount));
            try (Connection conn =DatabaseController.conn(); PreparedStatement pst = conn.prepareStatement("SELECT Quantity FROM product WHERE ProductID=?")) {
                pst.setString(1, id);
                pst.executeQuery();
                updatedQty = inventory - qty;
                try (PreparedStatement pst1 = conn.prepareStatement("UPDATE product SET Quantity=? WHERE ProductID=?")) {
                    pst1.setString(1, String.valueOf(updatedQty));
                    pst1.setString(2, id);
                    pst1.executeUpdate();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, "please Enter Vaild Number");
        }
    }
    
    
    

}
