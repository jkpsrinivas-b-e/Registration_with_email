package com.example.emailRegistration.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendOtpEmail(String to) {
//        String otp = String.valueOf(100000 + new Random().nextInt(900000)); Normal method
        SecureRandom secureRandom = new SecureRandom();
        int otpInt = secureRandom.nextInt(1_000_000);
        String otp = String.format("%06d", otpInt);
        String subject = "OTP for Email verification";
        String message = """
                Hi,<br>
                Thank you bhai for showing interest in our service.<br>
                Please use the given One Time Password to verify your login and create your ptofile.<br>
                 <b>OTP:</b>""" + " " + otp;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
            return otp;
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}