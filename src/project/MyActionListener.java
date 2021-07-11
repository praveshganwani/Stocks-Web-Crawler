package project;

import controller.AlertsController;
import controller.UpdatesController;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//implementing ActionListener Class for button clicks
class MyActionListener implements ActionListener {
    
    //default variables for the updating values on the Dashboard
    int flag;
    JFrame jf = new JFrame();
    JLabel stockname,stockmarket,stockabbr,currencytype,updatedon,stockprice,change,percentchange;
    Box bx = Box.createVerticalBox();
    JButton jb, jb1;
    String username, useremail, stock;
    
    //constructor for initializing the userdata
    public MyActionListener(String username, String useremail){
        this.flag=0;
        this.jb = new JButton("Get Alerts");
        this.jb1 = new JButton("Get Updates");
        this.username = username;
        this.useremail = useremail;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(flag);
        if(flag==0){
            JButton btn = (JButton)e.getSource();
            stock = ""+btn.getClientProperty("index");
            //connecting with the database for getting user wise stock data
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
                PreparedStatement ps = con.prepareStatement("select * from stocks where stockname=?");
                ps.setString(1, stock);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    stockname = new JLabel("STOCKNAME: "+rs.getString("stockname"), SwingConstants.CENTER);
                    stockmarket = new JLabel("STOCKMARKET: "+rs.getString("stockmarket"), SwingConstants.CENTER);
                    stockabbr = new JLabel("ABBREVIATION: "+rs.getString("stockabbr"), SwingConstants.CENTER);
                    currencytype = new JLabel("CURRENCY TYPE: "+rs.getString("currencytype"), SwingConstants.CENTER);
                    updatedon = new JLabel("LAST UPDATE: "+rs.getString("updatedon"), SwingConstants.CENTER);
                    stockprice = new JLabel("PRICE: "+rs.getDouble("stockprice"), SwingConstants.CENTER);
                    change = new JLabel("CHANGE: "+rs.getDouble("change"), SwingConstants.CENTER);
                    percentchange = new JLabel("PERCENT CHANGE: "+rs.getDouble("percentchange")+"%", SwingConstants.CENTER);
                }
                con.close();
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
            
            //setting of fonts and font sizes
            stockname.setFont(new Font("Dialog",Font.PLAIN,20));
            stockmarket.setFont(new Font("Dialog",Font.PLAIN,20));
            stockabbr.setFont(new Font("Dialog",Font.PLAIN,20));
            currencytype.setFont(new Font("Dialog",Font.PLAIN,20));
            updatedon.setFont(new Font("Dialog",Font.PLAIN,20));
            stockprice.setFont(new Font("Dialog",Font.PLAIN,20));
            change.setFont(new Font("Dialog",Font.PLAIN,20));
            percentchange.setFont(new Font("Dialog",Font.PLAIN,20));
            jb.setFont(new Font("Dialog",Font.BOLD,15));
            jb1.setFont(new Font("Dialog",Font.BOLD,15));
            
            bx.add(Box.createHorizontalStrut(80));
            bx.add(Box.createVerticalStrut(20));
            bx.add(stockname);
            bx.add(Box.createVerticalStrut(20));
            bx.add(stockmarket);
            bx.add(Box.createVerticalStrut(20));
            bx.add(stockabbr);
            bx.add(Box.createVerticalStrut(20));
            bx.add(currencytype);
            bx.add(Box.createVerticalStrut(20));
            bx.add(updatedon);
            bx.add(Box.createVerticalStrut(20));
            bx.add(stockprice);
            bx.add(Box.createVerticalStrut(20));
            bx.add(change);
            bx.add(Box.createVerticalStrut(20));
            bx.add(percentchange);
            bx.add(Box.createVerticalStrut(20));
            bx.add(jb);
            bx.add(Box.createVerticalStrut(20));
            bx.add(jb1);
            bx.add(Box.createVerticalStrut(40));
            
            //adding actionlisteners to the buttons
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //call to alerts controller for adding the alerts into the database
                    AlertsController ac = new AlertsController(jf, useremail, stock);
                }
            });
            
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //call to uodates controller for adding the updates into the database
                    UpdatesController uc = new UpdatesController(jf, useremail, stock);
                }
            });
            
            jf.add(bx);
            jf.setSize(600,550);
            jf.setLocationRelativeTo(null);
        }
        jf.setVisible(true);
        flag=1;
    }
}    