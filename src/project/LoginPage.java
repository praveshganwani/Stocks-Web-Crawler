package project;

import controller.LoginController;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;


//implementing ActionListener Class for button clicks
public class LoginPage implements ActionListener{
    
    //default variables for the login page
    JFrame jf;
    JLabel ulabel, plabel, text, details;
    JTextField username;
    JPasswordField password;
    JButton login, signup;
    JSeparator js;
    
    //constructor for initializing the login page
    public LoginPage(){          
        jf = new JFrame("Stock Alert System | Login Page");
        ulabel = new JLabel("Email ID");
        plabel = new JLabel("Password");
        text = new JLabel("New User?");
        details = new JLabel("<html><center>This is a Stock Alert System App for stocks of 10 Indian Tech Companies. Stocks are up to date w.r.t NSE Stocks<br>Registered Users can set alerts and update alerts for these stocks.<br>Alerts are sent on registered E-mail IDs</center></html>", SwingConstants.CENTER);
        js = new JSeparator();
        js.setOrientation(SwingConstants.HORIZONTAL);
        js.setBounds(20, 270, 340, 5);
        
        //setting of fonts and font sizes
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 15)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 15)));
        ulabel.setFont(new Font("Dialog",Font.PLAIN,15));
        plabel.setFont(new Font("Dialog",Font.PLAIN,15));
        text.setFont(new Font("Dialog",Font.PLAIN,15));
        details.setFont(new Font("Dialog",Font.PLAIN,15));
        
        username = new JTextField();
        password = new JPasswordField();
        
        login = new JButton("Login");
        signup = new JButton("Sign Up Here");
        
        //setting bounds for the JComponents on the page
        ulabel.setBounds(90, 50, 100, 30);
        plabel.setBounds(90, 100, 100, 30);
        text.setBounds(155, 195, 100, 30);
        details.setBounds(0, 275, 380, 110);
        
        username.setBounds(190, 50, 100, 30);
        password.setBounds(190, 100, 100, 30);
        
        login.setBounds(90, 150, 200, 30);
        signup.setBounds(115, 230, 150, 30);
        
        //adding the components in the JFrame
        jf.add(ulabel);
        jf.add(plabel);
        jf.add(details);
        jf.add(username);
        jf.add(password);
        jf.add(login);
        jf.add(signup);
        jf.add(js);
        jf.add(text);
        
        //adding actionlisteners to the buttons
        login.addActionListener(this);
        signup.addActionListener(this);
        jf.setSize(400,430);
        jf.setLayout(null);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        LoginPage lp = new LoginPage();
    }
    
    //overriden method from ActionListener to generate next page on butoon click
    @Override    
    public void actionPerformed(ActionEvent e) {
        //getting the source of the button click
        JButton jb = (JButton)e.getSource();
        if(jb.getText().equals("Sign Up Here")){
            jf.dispose();
            //redirecting to the registration page
            new RegistrationPage();
        }
        else if(jb.getText().equals("Login")){
            //call to logincontroller for checking the details
            LoginController lc = new LoginController();
            //checking login credentials
            if(lc.checkLogin(username.getText(), password.getText(), jf)){
                jf.dispose();
                //successfull login the redirect to user dashboard
                JOptionPane.showMessageDialog(jf, "Login Successful!", "Success", JOptionPane.PLAIN_MESSAGE);
                DashBoard db = new DashBoard(username.getText());
            }
        }
    }
}
