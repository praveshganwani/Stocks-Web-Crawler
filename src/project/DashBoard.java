package project;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashBoard{
   
    //default variables for the dashboard
    JFrame jf;
    JButton jb1, jb2, jb3, logout;
    JPanel jp1, jp2, jp3;
    JLabel welcome, userid, username, email, mobileno, updatetriggers, alerttriggers;
    JLabel[] title = new JLabel[10];
    JButton[] clicker = new JButton[10];
    String useremail;
    
    //constructor for initializing the dashboard
    public DashBoard(String uname){
        jf = new JFrame("User DashBoard");
        jb1 = new JButton("Home Page");
        jb2 = new JButton("Your Subscriptions");
        jb3 = new JButton("Profile");   
        logout = new JButton("Log Out");
        welcome = new JLabel("Welcome "+uname, SwingConstants.CENTER);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp3.setLayout(new BoxLayout(jp3, BoxLayout.Y_AXIS));
        Box box = Box.createVerticalBox();
        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createVerticalBox();
        
        //generating the dashboard for each user by connecting to the database
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/projectdb","projectdb","projectdb");
            PreparedStatement ps = con.prepareStatement("select * from users where username=?");
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                userid = new JLabel("USERID: "+rs.getInt("userid"), SwingConstants.CENTER);
                username = new JLabel("USERNAME: "+rs.getString("username"), SwingConstants.CENTER);
                useremail = rs.getString("email");
                email = new JLabel("EMAIL: "+rs.getString("email"), SwingConstants.CENTER);
                mobileno = new JLabel("MOBILE NO: "+rs.getLong("mobileno"), SwingConstants.CENTER);
            }
            ps = con.prepareStatement("select triggers from updates NATURAL JOIN users where username=?");
            ps.setString(1, uname);
            rs = ps.executeQuery();
            if(rs.next()) {
                updatetriggers = new JLabel("Total Update Triggers Sent: "+rs.getInt("triggers"), SwingConstants.CENTER);
            }
            ps = con.prepareStatement("select * from alerts NATURAL JOIN users where username=?");
            ps.setString(1, uname);
            rs = ps.executeQuery();
            if(rs.next()) {
                int sum = rs.getInt("maxtriggers") + rs.getInt("mintriggers");
                alerttriggers = new JLabel("Total Alert Triggers Sent: "+sum, SwingConstants.CENTER);
            }
            ps = con.prepareStatement("select * from stocks");
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()){
                System.out.println(rs.getString("stockname"));
                title[i] = new JLabel(""+rs.getString("stockname"));
                clicker[i] = new JButton("Get Details");
                clicker[i].putClientProperty("index", title[i].getText());
                //adding actionlisteners to the buttons for a call to MyActionListen
                clicker[i].addActionListener(new MyActionListener(uname, useremail));
                title[i].setFont(new Font("Dialog", Font.PLAIN, 20));
                i++;
            }   
            con.close();
        } 
         catch (Exception e) {
            System.out.println(e);
        }
        
        //setting of fonts and font sizes
        jb1.setFont(new Font("Dialog",Font.BOLD,15));
        jb2.setFont(new Font("Dialog",Font.BOLD,15));
        jb3.setFont(new Font("Dialog",Font.BOLD,15));
        logout.setFont(new Font("Dialog",Font.BOLD,15));
        welcome.setFont(new Font("Dialog",Font.BOLD,25));
        userid.setFont(new Font("Dialog",Font.PLAIN,20));
        username.setFont(new Font("Dialog",Font.PLAIN,20));
        email.setFont(new Font("Dialog",Font.PLAIN,20));
        mobileno.setFont(new Font("Dialog",Font.PLAIN,20));
        
        //setting bounds for the JComponents on the page
        jb1.setBounds(0, 331, 400, 30);
        jb2.setBounds(400, 331, 400, 30);
        jb3.setBounds(800, 331, 400, 30);
        logout.setPreferredSize(new Dimension(500,30));
        
        welcome.setBounds(0, 0, 1200, 30);
        
        userid.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        mobileno.setAlignmentX(Component.CENTER_ALIGNMENT);
        if(updatetriggers != null)
            updatetriggers.setAlignmentX(Component.CENTER_ALIGNMENT);
        if(alerttriggers != null)
            alerttriggers.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        box.add(Box.createVerticalStrut(10));
        box1.add(Box.createVerticalStrut(10));
        box2.add(Box.createVerticalStrut(10));
        box3.add(Box.createVerticalStrut(10));
        for(int i=0; i<5; i++){
            box.add(title[i]);
            box.add(Box.createVerticalStrut(25));
            box1.add(clicker[i]);
            box1.add(Box.createHorizontalStrut(20));
            box1.add(Box.createVerticalStrut(25));
        }   
        for(int i=5; i<10; i++){
            box2.add(Box.createHorizontalStrut(200));
            box2.add(title[i]);
            box2.add(Box.createVerticalStrut(25));
            box3.add(clicker[i]);
            box3.add(Box.createHorizontalStrut(20));
            box3.add(Box.createVerticalStrut(25));
        }   
        
        //adding the components in the JPanel
        jp3.add(Box.createVerticalStrut(20));
        jp3.add(userid);
        jp3.add(Box.createVerticalStrut(10));
        jp3.add(username);
        jp3.add(Box.createVerticalStrut(10));
        jp3.add(email);
        jp3.add(Box.createVerticalStrut(10));
        jp3.add(mobileno);
        if(updatetriggers != null)
        {   
            jp3.add(Box.createVerticalStrut(10));
            jp3.add(updatetriggers);
        }
        if(alerttriggers != null)
        {
            jp3.add(Box.createVerticalStrut(10));
            jp3.add(alerttriggers);
        }
        jp3.add(Box.createVerticalStrut(20));
        jp3.add(logout);
        jp3.setBounds(250, 50, 700, 400);
        jp3.setVisible(false);
        
        //adding the components in the JPanel
        jp1.add(box);
        jp1.add(box1);
        jp1.add(box2);
        jp1.add(box3);
        jp1.setBorder(BorderFactory.createTitledBorder("Active Stocks"));
        jp1.setBounds(0, 30, 1182, 300);
        jp1.setVisible(true);
        
        jp2.setBounds(0,30,1182,300);
        
        //adding the JPanels to the JFrame
        jf.add(welcome);
        jf.add(jb1);
        jf.add(jb2);
        jf.add(jb3);
        jf.add(jp1);
        jf.add(jp2);
        jf.add(jp3);
        
        //adding actionlisteners to the buttons
        jb1.addActionListener((ActionEvent e) -> {
            jp1.setVisible(true);
            jp2.setVisible(false);
            jp3.setVisible(false);
        });
        
        jb2.addActionListener((ActionEvent e) -> {
            //call to subscriptions page
            new Subscriptions(jf, jp2, useremail);
            jp1.setVisible(false);
            jp2.setVisible(true);
            jp3.setVisible(false);
        });
        
        jb3.addActionListener((ActionEvent e) -> {
            jp1.setVisible(false);
            jp2.setVisible(false);
            jp3.setVisible(true);
        });
        
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                JOptionPane.showMessageDialog(jf, "Logged Out Successfully!");
                new LoginPage();
            }
        });
        
        jf.setSize(1200,400);
        jf.setLayout(null);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}    