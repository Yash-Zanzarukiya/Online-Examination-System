package com.yashpz.examination_system.examination_system.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailSendFrom;

    public EmailService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    public void sendMail(String to, String subject, String body){
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailSendFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}