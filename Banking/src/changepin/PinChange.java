
package changepin;

import deposit.*;
import withdraw.*;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class PinChange{
    
    public static void sendMail(String recepient,String acc,String date) throws MessagingException
    {
        System.out.println("Preparing to send Email.");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "contact.acebank@gmail.com";
        String password ="acebank@101128";
        int ac=Integer.parseInt(acc);
        Session session = Session.getInstance(properties,new Authenticator(){
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(myAccountEmail,password);
            
        }
        });
        Message message = prepareMessage(session, myAccountEmail, recepient,ac,date);
        
        Transport.send(message);
        System.out.println("Message Sent Successfully");
        
        
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,int acc,String date){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Pin Changed");
            message.setText("PIN of  your A/c XXXXX"+(acc%10000)+" has been changed on  "+date+".\n Please contact the helpline if you find it suspicious"+"\n-Ace Bank");
            return message;
        }catch(Exception ex)
        {
            System.out.println("Theres been a error");
            return null;
            
            
       
    }
        
        
    }
    
}
