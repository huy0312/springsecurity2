package com.huynguyen.springsecurity2.service;


import com.huynguyen.springsecurity2.entity.User;

public interface EmailService {

    public void sendEmail(User user, String subject);

    public String generateVerificationCode();
}
