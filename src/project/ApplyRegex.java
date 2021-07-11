package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.UserData;

public class ApplyRegex {
    
    //method to validate the details inside UserData model using regex. returns true if all valid
    public boolean validate(UserData ud, JFrame jf){
        Pattern name = Pattern.compile(".");
        Pattern pass = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&]).{6,20}$");
        Pattern mob = Pattern.compile("^\\d{10}$");
        Pattern em = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        Matcher m = name.matcher(ud.getUsername());
        Matcher m1 = pass.matcher(ud.getPassword());
        Matcher m2 = mob.matcher(""+ud.getMobileno());
        Matcher m3 = em.matcher(ud.getEmail());
        if(!m.find()){
            JOptionPane.showMessageDialog(jf, "Enter Proper Username", "Error", JOptionPane.WARNING_MESSAGE);  
            return false;
        }
        if(!m3.find()){
            JOptionPane.showMessageDialog(jf, "Enter Valid Email ID", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!m1.find()){
            JOptionPane.showMessageDialog(jf, "Password Too Weak", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!m2.find()){
            JOptionPane.showMessageDialog(jf, "Enter Valid Mobile No.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }   
}
