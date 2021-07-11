package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginController {
    
    //method to check the login credentials. returns true if verification successful
    public boolean checkLogin(String username, String password, JFrame jf){
        int flag=0;
        //establishing the connection with the database
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
            PreparedStatement ps = con.prepareStatement("select username from users");
            //executing the query for selecting the registered user
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(username.equals(rs.getString("username")))
                    flag=1;
            }
            if(flag==0){
                JOptionPane.showMessageDialog(jf, "User Not Found! Please Register First", "Invalid User", JOptionPane.ERROR_MESSAGE);
                con.close();
                return false;
            }
            else{
                ps = con.prepareStatement("select password from users where username=?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if(rs.next()){
                    if(password.equals(rs.getString("password"))){
                        con.close();
                        return true;
                   }
                    else{
                        JOptionPane.showMessageDialog(jf, "Password is incorrect!", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                        con.close();
                        return false;
                    }
                }
                con.close();
                return false;
            }
        } 
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
