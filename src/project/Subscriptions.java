package project;

import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Subscriptions {
    
    //default variables for the subscriptions page
    int flag;
    JLabel[] alerts = new JLabel[10];
    JButton[] change = new JButton[10];
    JButton[] delete = new JButton[10];
    JLabel[] updates = new JLabel[10];
    JButton[] change1 = new JButton[10];
    JButton[] delete1 = new JButton[10];
    JPanel alert = new JPanel();
    JPanel update = new JPanel();

    //constructor for initializing the subscriptions page
    public Subscriptions(JFrame jf, JPanel jp, String useremail){
            jp.removeAll();
            Box box = Box.createVerticalBox();
            Box box1 = Box.createVerticalBox();
            Box box2 = Box.createVerticalBox();
            Box box3 = Box.createVerticalBox();
            Box box4 = Box.createVerticalBox();
            Box box5 = Box.createVerticalBox();
            
            box.add(Box.createVerticalStrut(10));
            box1.add(Box.createVerticalStrut(10));
            box2.add(Box.createVerticalStrut(10));
            box3.add(Box.createVerticalStrut(10));
            box4.add(Box.createVerticalStrut(10));
            box5.add(Box.createVerticalStrut(10));
            
            //initializing the alerts and the updates section by connecting to the database
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
                PreparedStatement ps = con.prepareStatement("select * from alerts where useremail=?");
                ps.setString(1, useremail);
                ResultSet rs = ps.executeQuery();
                int i=0;
                while(rs.next()){
                    alerts[i] = new JLabel(rs.getString("stockname"));
                    alerts[i].setFont(new Font("Dialog", Font.PLAIN, 15));
                    change[i] = new JButton("Change");
                    change[i].setFont(new Font("Dialog", Font.BOLD, 10));
                    change[i].putClientProperty("alert_change", rs.getString("stockname"));
                    //call to alertchangelistener for changing the alerts
                    change[i].addActionListener(new AlertChangeListener(useremail));
                    delete[i] = new JButton("Delete");
                    delete[i].setFont(new Font("Dialog", Font.BOLD, 10));
                    delete[i].putClientProperty("alert_delete", rs.getString("stockname"));
                    //call to alertchangelistener for deleting the alerts
                    delete[i].addActionListener(new AlertChangeListener(useremail));
                    box.add(alerts[i]);
                    box.add(Box.createVerticalStrut(25));
                    box1.add(change[i]);
                    box1.add(Box.createHorizontalStrut(10));
                    box1.add(Box.createVerticalStrut(20));
                    box2.add(delete[i]);
                    box2.add(Box.createHorizontalStrut(10));
                    box2.add(Box.createVerticalStrut(20));
                    i++;
                }
                ps = con.prepareStatement("select * from updates where useremail=?");
                ps.setString(1, useremail);
                rs = ps.executeQuery();
                i=0;
                while(rs.next()){
                    updates[i] = new JLabel(rs.getString("stockname"));
                    updates[i].setFont(new Font("Dialog", Font.PLAIN, 15));
                    change1[i] = new JButton("Change");
                    change1[i].setFont(new Font("Dialog", Font.BOLD, 10));
                    change1[i].putClientProperty("update_change", rs.getString("stockname"));
                    //call to updatechangelistener for changing the updates
                    change1[i].addActionListener(new UpdateChangeListener(useremail));
                    delete1[i] = new JButton("Delete");
                    delete1[i].setFont(new Font("Dialog", Font.BOLD, 10));
                    delete1[i].putClientProperty("update_delete", rs.getString("stockname"));
                    //call to updatechangelistener for deleting the updates
                    delete1[i].addActionListener(new UpdateChangeListener(useremail));
                    box3.add(updates[i]);
                    box3.add(Box.createVerticalStrut(25));
                    box4.add(change1[i]);
                    box4.add(Box.createHorizontalStrut(10));
                    box4.add(Box.createVerticalStrut(20));
                    box5.add(delete1[i]);
                    box5.add(Box.createHorizontalStrut(10));
                    box5.add(Box.createVerticalStrut(20));
                    i++;
                }
                con.close();
            }   
            catch (Exception e) {
                System.out.println(e);
            }
            
            //adding the details to updates and alerts Boxes
            alert.add(box);
            alert.add(box1);
            alert.add(box2);
            alert.setBorder(BorderFactory.createTitledBorder("Alerts"));
            alert.setVisible(true);
            JScrollPane jsp1 = new JScrollPane(alert, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jsp1.setPreferredSize(new Dimension(500,295));
            
            update.add(box3);
            update.add(box4);
            update.add(box5);
            update.setBorder(BorderFactory.createTitledBorder("Updates"));
            update.setVisible(true);
            JScrollPane jsp2 = new JScrollPane(update, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jsp2.setPreferredSize(new Dimension(500,295));
            
            //adding all the components to JPanel
            jp.add(jsp1);
            jp.add(jsp2);
            jp.revalidate();
            jp.repaint();
    }
}
