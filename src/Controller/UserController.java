package Controller;

import javax.swing.*;
import Model.UserModel;
import View.CashierView;
import View.LoginView;
import View.ManagerView;
import java.sql.*;

public class UserController {

    private UserModel userModel;
    boolean isDone = false;

    public UserController(UserModel user) {

        this.userModel = user;
    }
//login control method

    public void Login() {
        if ("Select Your Role".equals(userModel.getUserRole())) {
            JOptionPane.showMessageDialog(null, "select Your Role");
        } else if ("".equals(userModel.getUsername())) {
            JOptionPane.showMessageDialog(null, "Username required");
        } else if ("".equals(userModel.getPassword())) {
            JOptionPane.showMessageDialog(null, "Password required");

        } else {
            try (Connection conn1 = DatabaseController.conn(); PreparedStatement pst = conn1.prepareStatement("SELECT * FROM login WHERE Username=? AND Password=? AND User_Role=?")) {
                ResultSet rs;
                pst.setString(1, userModel.getUsername());
                pst.setString(2, userModel.getPassword());
                pst.setString(3, userModel.getUserRole());
                rs = pst.executeQuery();
                if (rs.next()) {
                    ManagerView MDash = new ManagerView();
                    CashierView CDash = new CashierView();
                    LoginView log = new LoginView();
                    if ("Manager".equals(userModel.getUserRole())) {

                        isDone = true;
                        JOptionPane.showMessageDialog(null, "Successfully Login " + userModel.getUsername());
                        MDash.setVisible(true);

                    } else if ("Cashier".equals(userModel.getUserRole())) {

                        isDone = true;
                        JOptionPane.showMessageDialog(null, "Successfully Login " + userModel.getUsername());
                        CDash.setVisible(true);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed, Please Check Your username,password and user role");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

//    create account method
    public void CreateAccount() {

        if ("Select User Role".equals(userModel.getUserRole())) {
            JOptionPane.showMessageDialog(null, "select User Role");
        } else if ("".equals(userModel.getUsername())) {
            JOptionPane.showMessageDialog(null, "Username required");
        } else if ("".equals(userModel.getPassword())) {
            JOptionPane.showMessageDialog(null, "Password required");

        } else {

            try (Connection conn = DatabaseController.conn(); PreparedStatement pst = conn.prepareStatement("INSERT INTO login(Username,Password,User_Role) VALUES(?,?,?)")) {

                pst.setString(1, userModel.getUsername());
                pst.setString(2, userModel.getPassword());
                pst.setString(3, userModel.getUserRole());
                pst.executeUpdate();
                isDone = true;
                JOptionPane.showMessageDialog(null, "Account Created Successful..!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    public boolean checkingCreate() {
        return isDone;
    }
}
