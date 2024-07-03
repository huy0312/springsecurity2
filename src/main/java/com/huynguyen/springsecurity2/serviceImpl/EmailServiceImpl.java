package com.huynguyen.springsecurity2.serviceImpl;

import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(User user, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setSubject("Email Verification Code");
        message.setText("Your verification code is: " + user.getVerificationCode());
        mailSender.send(message);
    }

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 +  random.nextInt(900000);
        return String.valueOf(code);
    }
}
