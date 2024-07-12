package Model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import View.CashierView;
import View.ManagerView;

public class ProductModel {

    CashierView cdash = new CashierView();
    ManagerView mdash=new ManagerView();
    
    String productId;
    String productName;
    String productCategory;
    double productPrice;
    int productQty;

    public ProductModel(String productId, String productName, String productCategory, double productPrice, int productQty) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    

   
}
