package MainCodeBase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TestController {

    @FXML
    private Button sendBt;

    @FXML
    private TextArea mailText;

    @FXML
    private TextField email;

    public void sendEmail(){

        String to = email.getText().toString();
        String from = "daniadamwolfgang@gmail.com";
        String host = "smtp.gmail.com";

        String user = "daniadamwolfgang@gmail.com";
        String pass = "TrecoolART123!";

        String subject = "Test Email";

            String yPass = "266653";
            String yUser = "hahaha@gmail.com";
        Properties props =  System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smpt.port", "587");

        Session sess = Session.getDefaultInstance(props, new javax.mail.Authenticator(){

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {

            MimeMessage m = new MimeMessage(sess);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setText("Username: "+yUser+"\nPassword: "+yPass);


            Transport.send(m);

        }catch(MessagingException e){
            e.printStackTrace();

        }



    }

    public void sendBtFunc(){

    }

}
