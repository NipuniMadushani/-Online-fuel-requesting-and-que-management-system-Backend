package com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services;


import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.EmailDTO;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;


@Service
@Slf4j
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    private final String senderMail = "nipunimadushani52@gmail.com";
    private final String mailSubject = "Fule Station verification code";

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        try {

            message.setFrom(senderMail);
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);

            javaMailSender.send(message);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("mail sent successfully..");
    }

    public void sendEmailWithTemplate(EmailDTO mail) {
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mailSubject);
            mimeMessageHelper.setFrom(senderMail);
            mimeMessageHelper.setTo(mail.getTo());
            mail.setContent(getContentFromTemplate(mail.getModel()));
            mimeMessageHelper.setText(mail.getContent(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("mail sent successfully..");
    }

    public String getContentFromTemplate(Map< String, Object > model)     {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("email.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("success..");
        return content.toString();
    }
}
