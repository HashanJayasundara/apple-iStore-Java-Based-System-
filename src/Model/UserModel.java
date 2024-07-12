package Model;

import View.LoginView;
import java.sql.*;
import javax.swing.JOptionPane;

public class UserModel {

    String username;
    String password;
    String userRole;
    boolean isLog = false;
    boolean isManager = false;
    boolean isCashier = false;

    LoginView log = new LoginView();

    public UserModel(String username, String password, String userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
    
    
    // get methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }
    
    
    //set method

    public void setUserDetails(String username, String password, String userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;

    }

    public boolean isLogged() {
        return isLog;

    }

    public boolean isUserManager() {
        return isManager;
    }

    public boolean isUserCashier() {
        return isCashier;
    }
}
