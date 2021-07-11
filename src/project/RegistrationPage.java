package project;

import controller.RegistrationController;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import model.UserData;

//implementing ActionListener Class for button clicks
public class RegistrationPage implements ActionListener{
    
    //default variables for the registration page
    JFrame jf;
    JLabel ulabel, elabel, plabel, rplabel, mlabel;
    JTextField username, email, mobileno;
    JPasswordField password, repassword;
    JButton register, goback;
    
    //constructor for initializing the registration page
    public RegistrationPage(){
        jf = new JFrame("Registration Page");
        ulabel = new JLabel("Enter User Name");
        elabel = new JLabel("Enter Email ID");
        plabel = new JLabel("Set Password");
        rplabel = new JLabel("Re-enter Password");
        mlabel = new JLabel("Enter Mobile No.");
        
        //setting of fonts and font sizes
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 15)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 15)));
        ulabel.setFont(new Font("Dialog",Font.PLAIN,15));
        elabel.setFont(new Font("Dialog",Font.PLAIN,15));
        plabel.setFont(new Font("Dialog",Font.PLAIN,15));
        rplabel.setFont(new Font("Dialog",Font.PLAIN,15));
        mlabel.setFont(new Font("Dialog",Font.PLAIN,15));
        
        username = new JTextField();
        email = new JTextField();
        mobileno = new JTextField();
        password = new JPasswordField();
        repassword = new JPasswordField();
        
        register = new JButton("Register");
        goback = new JButton("Go Back To Login");
        
        //setting bounds for the JComponents on the page
        ulabel.setBounds(70, 120, 150, 30);
        elabel.setBounds(70, 170, 150, 30);
        plabel.setBounds(70, 220, 150, 30);
        rplabel.setBounds(70, 270, 150, 30);
        mlabel.setBounds(70, 320, 150, 30);
        
        username.setBounds(250, 120, 200, 30);
        email.setBounds(250, 170, 200, 30);
        password.setBounds(250, 220, 200, 30);
        repassword.setBounds(250, 270, 200, 30);
        mobileno.setBounds(250, 320, 200, 30);
        
        register.setBounds(70, 400, 380, 30);
        goback.setBounds(160, 450, 200, 30);
        
        //adding the components in the JFrame
        jf.add(ulabel);
        jf.add(elabel);
        jf.add(plabel);
        jf.add(rplabel);
        jf.add(mlabel);
        jf.add(username);
        jf.add(email);
        jf.add(password);
        jf.add(repassword);
        jf.add(mobileno);
        jf.add(register);
        jf.add(goback);
        
        //adding actionlisteners to the buttons
        register.addActionListener(this);
        goback.addActionListener(this);
        jf.setSize(525, 600);
        jf.setLayout(null);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        RegistrationPage rp = new RegistrationPage();
    }
    
    //overriden method from ActionListener to generate next page on butoon click
    @Override
    public void actionPerformed(ActionEvent e) {
        //getting the source of the button click
        JButton jb = (JButton)e.getSource();
        if(jb.getText().equals("Register"))
        {
            if((password.getText().equals(repassword.getText())))
            {
                //storing all values into the UserData model
                UserData ud = new UserData();
                ud.setUsername(username.getText());
                ud.setEmail(email.getText());
                ud.setPassword(password.getText());
                ud.setMobileno(Long.parseLong(mobileno.getText()));
                //call to registration controller for validating the details inside the UserData model
                RegistrationController rc = new RegistrationController();
                rc.validateDetails(ud, jf);
            }
            else
                JOptionPane.showMessageDialog(jf, "Passwords not matching!");
        }
        else{
            jf.dispose();
            new LoginPage();
        }
    }
}
