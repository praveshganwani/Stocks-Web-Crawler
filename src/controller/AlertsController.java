package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class AlertsController {
    
    //constructor adding the alerts into the database
    public AlertsController(JFrame jf, String useremail, String stock){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
            PreparedStatement ps = con.prepareStatement("select * from alerts where stockname=? and useremail=?");
            ps.setString(1, stock);
            ps.setString(2, useremail);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(jf, "Alerts for the stock already set");
            }
            else{
                String max=null;
                String min = JOptionPane.showInputDialog("Min Stock Price for Alerts");
                if(min!=null && !min.equals(""))
                    max = JOptionPane.showInputDialog("Max Stock Price for Alerts");
                if(min!=null && max!=null && !max.equals("") && !min.equals("")){
                    ps = con.prepareStatement("insert into alerts (useremail,stockname,minimum,maximum) values(?,?,?,?)");
                    ps.setString(1, useremail);
                    ps.setString(2, stock);
                    ps.setDouble(3, Double.parseDouble(min));
                    ps.setDouble(4, Double.parseDouble(max));
                    ps.executeUpdate();
                    con.close();
                    JOptionPane.showMessageDialog(jf, "Alerts will be sent to "+useremail);
                }
                else
                    JOptionPane.showMessageDialog(jf, "Set appropriate value", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
