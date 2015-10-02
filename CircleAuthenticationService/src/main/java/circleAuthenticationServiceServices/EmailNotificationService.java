package circleAuthenticationServiceServices;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.stereotype.Service;

@Service("EmailNotificationService")
public class EmailNotificationService {
    static final String FROM = "wenchaozhang@ufl.edu";
  //  static final String TO = "wenchaozhang@ufl.edu";   
                                                      
    
    static final String BODY = "Your circle account has already be created successfully. Your username is your email you provided to us.";
    static final String SUBJECT = "Circle Account Created Email Notification";
    static final String SMTP_USERNAME = "AKIAJ372Y7QIGGIKMIXQ";  
    static final String SMTP_PASSWORD = "Avpo2rdGPy8rQBcjPmPemS0e6PN4FvjgwmxSsdSIX4tQ";   
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    static final int PORT = 587;
    
    public void sendAccountCreatedEmailToClient(String username) throws Exception {
    	
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/plain");
            
        // Create a transport.        
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
           // System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
           // System.out.println("Email sent!");
        }
        catch (Exception ex) {
           // System.out.println("The email was not sent.");
           // System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();        	
        }
    }
    
}
