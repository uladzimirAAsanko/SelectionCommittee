package by.sanko.selectioncommittee.util.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String FILE_JWT_CONFIG = "config/mail";
    private static final String MAIL_AUTH = "mail.smtp.auth";
    private static final String MAIL_SRARTTLS = "mail.smtp.starttls.enable";
    private static final String MAIL_HOST = "mail.smtp.host";
    private static final String MAIL_PORT = "mail.smtp.port";
    private static final String MAIL_USER_EMAIL = "mail.user.name";
    private static final String MAIL_USER_PASSWORD = "mail.user.password";
    private static final Properties properties = new Properties();

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FILE_JWT_CONFIG);
        properties.put(MAIL_AUTH,resourceBundle.getString(MAIL_AUTH));
        properties.put(MAIL_SRARTTLS,resourceBundle.getString(MAIL_SRARTTLS));
        properties.put(MAIL_HOST,resourceBundle.getString(MAIL_HOST));
        properties.put(MAIL_PORT,resourceBundle.getString(MAIL_PORT));
        properties.put(MAIL_USER_EMAIL,resourceBundle.getString(MAIL_USER_EMAIL));
        properties.put(MAIL_USER_PASSWORD,resourceBundle.getString(MAIL_USER_PASSWORD));
    }
    private MailSender(){}

    public static boolean sendMail(String recipientAddress, String text, String subject) {
        boolean answer = true;
        String userEmail = properties.getProperty(MAIL_USER_EMAIL);
        String password = properties.getProperty(MAIL_USER_PASSWORD);
        Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userEmail,password);
                }
            });
        Message message = prepeareMessage(session, userEmail, recipientAddress,text, subject);
        try {
            if(message == null){
                answer = false;
            }else {
                Transport.send(message);
            }
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error while sending email", e);
            answer = false;
        }
        return answer;
    }

    private static Message prepeareMessage(Session session, String userEmail, String recipientAddress, String text, String subject) {
        try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipientAddress));
            message.setText(text);
            message.setSubject(subject);
        return message;
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error while creating  email", e);
        }
        return null;
    }

}
