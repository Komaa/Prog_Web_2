/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author pietro
 */
public class MailUtility {
    
        
  public static void sendMail (String dest, String oggetto, String testoEmail) throws MessagingException{
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
// Creazione di una mail session
    // Get a Properties object

 Properties props = System.getProperties();

 props.setProperty("mail.smtp.host", "smtp.gmail.com");

 props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);

 props.setProperty("mail.smtp.socketFactory.fallback", "false");

 props.setProperty("mail.smtp.port", "465");

 props.setProperty("mail.smtp.socketFactory.port", "465");

 props.put("mail.smtp.auth", "true");

 props.put( "mail.debug", "true" );
 
   final String username = "test.prog2.unitn@gmail.com";

 final String password = "testprog2";

 Session session = Session.getDefaultInstance(props, new Authenticator(){

 protected PasswordAuthentication getPasswordAuthentication() {

 return new PasswordAuthentication(username, password);

 }});

 // — Create a new message –

 Message msg = new MimeMessage(session);

 // — Set the FROM and TO fields –

 msg.setFrom(new InternetAddress(username + ""));

 msg.setRecipients(Message.RecipientType.TO,

InternetAddress.parse(dest,false));

  
 msg.setSubject(oggetto);

 msg.setContent(testoEmail, "text/html; charset=utf-8");
 

 msg.setSentDate(new Date());

 Transport.send(msg);
  }
}

