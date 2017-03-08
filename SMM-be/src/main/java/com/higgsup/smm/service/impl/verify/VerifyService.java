package com.higgsup.smm.service.impl.verify;

import com.higgsup.smm.model.repo.VerifyUserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by StormSpirit on 1/11/2017.
 */
@Service
public class VerifyService {
    @Autowired
    VerifyUserRepository verifyUserRepository;

    public String sendVerifiedEmail(String emailDes, Multipart content) throws IOException, TemplateException, MessagingException {

        //Send tempalte to Email Destination
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("hungpt58.uet@gmail.com","hunghp1502");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hungpt58.uet@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDes));
            message.setSubject("You've been invited to Facebook's Manager App");
            message.setContent(content);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "success";
    }


}
