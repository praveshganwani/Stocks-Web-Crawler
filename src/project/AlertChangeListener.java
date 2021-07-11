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
class AlertChangeListener implements ActionListener{

    String min;
    String max;
    String stockname, delstockname, email;
    
    public AlertChangeListener(String email){
        this.email=email;
    }
    
    //changing/deleting alerts for respective user email
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        //checking the button source if its for changing or deleting the alert
        stockname = ""+btn.getClientProperty("alert_change");
        delstockname = ""+btn.getClientProperty("alert_delete");
        if(!stockname.equals("null")){
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
                PreparedStatement ps = con.prepareStatement("select * from alerts where stockname=? and useremail=?");
                ps.setString(1, stockname);
                ps.setString(2, email);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    min = rs.getString("minimum");
                    max = rs.getString("maximum");
                    min = JOptionPane.showInputDialog("Previous Min Value: "+min+"\nSet New Min Value");
                    if(min!=null && !min.equals(""))
                        max = JOptionPane.showInputDialog("Previous Max Value: "+max+"\nSet New Max Value");
                    if(min!=null && max!=null && !max.equals("") && !min.equals("")){
                        ps = con.prepareStatement("update alerts set minimum=?, maximum=? where stockname=? and useremail=?");
                        ps.setDouble(1, Double.parseDouble(min));
                        ps.setDouble(2, Double.parseDouble(max));
                        ps.setString(3, stockname);
                        ps.setString(4, email);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(btn, "Alert values changed successfully!");
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
                PreparedStatement ps = con.prepareStatement("delete from alerts where stockname=? and useremail=?");
                ps.setString(1, delstockname);
                ps.setString(2, email);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(btn, "Alert Deleted Successfully");
                con.close();
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
