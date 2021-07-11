package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UpdatesController {

    //constructor adding the updates into the database
    public UpdatesController(JFrame jf, String useremail, String stock){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
            PreparedStatement ps = con.prepareStatement("select * from updates where stockname=? and useremail=?");
            ps.setString(1, stock);
            ps.setString(2, useremail);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(jf, "Updates for the stock already set");
            }
            else{
                String mins = JOptionPane.showInputDialog("Set Interval(in mins)");
                if(mins!=null && !mins.equals("")){
                    ps = con.prepareStatement("insert into updates (useremail,stockname,mins,remainingmins) values(?,?,?,?)");
                    ps.setString(1, useremail);
                    ps.setString(2, stock);
                    ps.setInt(3, Integer.parseInt(mins));
                    ps.setInt(4, Integer.parseInt(mins));
                    ps.executeUpdate();
                    con.close();
                    JOptionPane.showMessageDialog(jf, "Updates will be sent to "+useremail);
                }
                else
                    JOptionPane.showMessageDialog(jf, "Set appropriate value", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
