package com.yashpz.examination_system.examination_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private static String emailSendFrom;

    public EmailService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    public void sendMail(String to, String subject, String body){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailSendFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}