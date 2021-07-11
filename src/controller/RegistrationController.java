package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.UserData;
import project.ApplyRegex;
import project.LoginPage;

public class RegistrationController {

    //method to validate the details inside UserData model
    public void validateDetails(UserData ud, JFrame jf){
        int flag=0;
        String username = ud.getUsername();
        String email = ud.getEmail();
        String password = ud.getPassword();
        long mobileno = ud.getMobileno();
        //establishing connection with the database to add the user to the database
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
            PreparedStatement ps = con.prepareStatement("select username,email from users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(username.equals(rs.getString("username"))){
                    JOptionPane.showMessageDialog(jf, "Username already taken. Please try a new one", "Error", JOptionPane.ERROR_MESSAGE);
                    flag=1;
                }
                if(email.equals(rs.getString("email"))){
                    JOptionPane.showMessageDialog(jf, "User/Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    flag=1;
                }
            }
            if(flag==0){
                //if new user the check for valid credentials by calling the ApplyRegex
                ApplyRegex ar = new ApplyRegex();
                if(ar.validate(ud, jf)){
                    //add user to database after successfull validation
                    ps = con.prepareStatement("insert into users (username,email,password,mobileno) values(?,?,?,?)");
                    ps.setString(1, username);
                    ps.setString(2, email);
                    ps.setString(3, password);
                    ps.setLong(4, mobileno);
                    ps.executeUpdate();
                    con.close();
                    JOptionPane.showMessageDialog(jf, "User Added Successfully! Login To Continue", "Success", JOptionPane.PLAIN_MESSAGE);
                    jf.dispose();
                    new LoginPage();
                }
            }
            con.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
