package com.higgsup.smm.service.impl.verify;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StormSpirit on 1/11/2017.
 */
public class BuildTemplate {
    public Multipart buildTemplateEmail(String nameUser, String role, String namePage, String url) throws IOException, TemplateException, MessagingException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setDirectoryForTemplateLoading(new File("template-email"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //Set Template Model
        TemplateModel templateModel = new TemplateModel();
        templateModel.setRole(role);
        templateModel.setNamePage(namePage);
        templateModel.setNameUser(nameUser);
        templateModel.setUrl(url);

        //Create Data Model
        Map<String, Object> map = new HashMap<>();
        List<TemplateModel> references = new ArrayList<>();
        references.add(templateModel);
        map.put("references", references);

        //Instantiate template
        Template template = cfg.getTemplate("template.ftl");
        Writer out = new StringWriter();
        template.process(map, out);

        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(out.toString(), "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        return multipart;
    }

}
