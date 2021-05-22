
package createaccount;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JoiningMail{
    
    public static void sendMail(String recepient,String name,String bal,String acct) throws MessagingException
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
        Message message = prepareMessage(session, myAccountEmail, recepient,name,bal,acct);
        
        Transport.send(message);
        System.out.println("Message Sent Successfully");
        
        
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String name,String bal,String acct){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("New Account Created");
            message.setText("Welcome "+name+",\n"+"Thank you so much for allowing us to help you with your recent account opening. We are committed to providing \nour customers with the highest level of service and the most innovative banking products possible.\n"+"We are very glad you chose us as your financial institution and hope you will take advantage of our wide variety of \nsavings, investment and loan products, all designed to meet your specific needs.\n\nAccount Type- "+acct+"\nCurrent Balance - ₹"+bal);
            return message;
        }catch(Exception ex)
        {
            System.out.println("Theres been a error");
            return null;
            
            
       
    }
        
        
    }
    
}
