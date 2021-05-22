
package OTP;

import withdraw.*;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class OtpMail{
    /**
     * sends One Time Password to recepient's  email address 
     * @param recepient
     * @return int 
     * @throws MessagingException 
     */
    public static int sendMail(String recepient) throws MessagingException
    {
        System.out.println("Preparing to send Email.");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "contact.acebank@gmail.com";
        String password ="acebank@101128";
        
        Session session = Session.getInstance(properties,new Authenticator(){
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(myAccountEmail,password);
            
        }
        });
         Random ran=new Random();
         int num = ran.nextInt(89999) + 10000;
         
        Message message = prepareMessage(session, myAccountEmail, recepient,num);
        
        Transport.send(message);
        System.out.println("Message Sent Successfully");
        
        return num;
        
        
    }
    /**
     * Drafts message to be sent to the recepient
     * @param session
     * @param myAccountEmail
     * @param recepient
     * @param otp
     * @return Message
     */
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,int otp){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("OTP Verification");
            message.setText("Hello ,\nYour one time password(otp) is -"+otp); 
            return message;
        }catch(Exception ex)
        {
            System.out.println("Theres been a error");
            return null;
            
            
       
    }
        
        
    }
    
}
