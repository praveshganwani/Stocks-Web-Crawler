package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;

//implementing ActionListener Class for button clicks
public class UpdateChangeListener implements ActionListener{
    
    String mins;
    String stockname, delstockname, email;
    
    public UpdateChangeListener(String email){
        this.email=email;
    }
    
    //changing/deleting alerts for respective user email
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        //checking the button source if its for changing or deleting the update
        stockname = ""+btn.getClientProperty("update_change");
        delstockname = ""+btn.getClientProperty("update_delete");
        if(!stockname.equals("null")){
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
                PreparedStatement ps = con.prepareStatement("select * from updates where stockname=? and useremail=?");
                ps.setString(1, stockname);
                ps.setString(2, email);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    mins = rs.getString("mins");
                    mins = JOptionPane.showInputDialog("Previous Update Interval: "+mins+"\nSet New Update Interval");
                    if(mins!=null && !mins.equals("")){
                        ps = con.prepareStatement("update updates set mins=? where stockname=? and useremail=?");
                        ps.setInt(1, Integer.parseInt(mins));
                        ps.setString(2, stockname);
                        ps.setString(3, email);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(btn, "Update Interval changed successfully!");
                    }   
                }
                con.close();
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
        else{
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
                PreparedStatement ps = con.prepareStatement("delete from updates where stockname=? and useremail=?");
                ps.setString(1, delstockname);
                ps.setString(2, email);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(btn, "Update Deleted Successfully");
                con.close();
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
