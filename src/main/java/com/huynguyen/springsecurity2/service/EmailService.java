package com.huynguyen.springsecurity2.service;


public interface EmailService {

    public void sendEmail(String to, String verificationCode);

}
