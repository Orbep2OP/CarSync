package util;

import employeeacess.DataSource;

import java.util.Random;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail {

    //SETUP MAIL SERVER PROPERTIES
    //DRAFT AN EMAIL
    //SEND EMAIL
    DataSource dataSource = new DataSource();
    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void sendEmail(Session newSession, MimeMessage mimeMessage) throws MessagingException {
        String fromUser = "imttprojectpolarising@gmail.com";  //Enter sender email id
        String fromUserPassword = "diogojosepedro";  //Enter sender gmail password , this will be authenticated by gmail smtp server
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!!!");
    }

    public MimeMessage draftEmail(String nif, Session newSession) throws  MessagingException {
        //Extract from table person the email of the person
        //Extract from table person the name of the person with the nif

        String name = dataSource.queryCustomersHashMap().get(nif).getName();
        String email = dataSource.queryCustomersHashMap().get(nif).getEmail();
        String emailSubject = "Password Recovery Request";
        String emailBody =
                "Dear " + name + ",\n" +
                        " We received a request to reset your password. Your new temporary password is: " + generateRandomPassword(12) + "\n\n" +
                        "        Please use this password to log in to your account. Once logged in, we recommend changing your password to something more secure.\n\n" +
                        "        If you did not request a password reset, please disregard this email.\n\n" +
                        "                Best regards,\n\n" +
                        "                The IMT Team";

        MimeMessage mimeMessage = new MimeMessage(newSession);

        mimeMessage = new MimeMessage(newSession);

        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject(emailSubject);

        // CREATE MIMEMESSAGE
        // CREATE MESSAGE BODY PARTS
        // CREATE MESSAGE MULTIPART
        // ADD MESSAGE BODY PARTS ----> MULTIPART
        // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object


        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "html/text") ;
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }

    public void setupServerProperties(Session newSession) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "547");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        this.newSession = Session.getDefaultInstance(properties, null);
    }


    // Generate a random password
    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}