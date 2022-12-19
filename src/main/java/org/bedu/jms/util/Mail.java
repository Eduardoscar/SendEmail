package org.bedu.jms.util;

import lombok.extern.slf4j.Slf4j;
import org.bedu.jms.model.Email;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class Mail {

    private String username = "garo970609@gmail.com";

    private String password = "dxfsscxeejdtxcog";

    public Mail() {
    }
    private Session connectServer() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols","TLSv1.2");
        return Session.getInstance(props,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    public void send(Email data) {
        try {
            log.info("Opening Server Connection ...");
            Session session = connectServer();
            log.info("Create email");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            log.info("Add Recipient: {}",data.getTo());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(data.getTo()));
            log.info("Add Subject: {}",data.getSubject());
            message.setSubject(data.getSubject());
            log.info("Add Body");
            message.setContent(data.getBody(), "text/html");
            log.info("Send email..");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com",username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            log.info("Closing Server Connection ...");
        } catch (Exception e) {
            log.error("{}",e.toString());
        }
    }
}
