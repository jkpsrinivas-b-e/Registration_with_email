package com.example.emailRegistration.controller;

import com.example.emailRegistration.service.EmailService;
import com.example.emailRegistration.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email, HttpSession session) {
        String otp = emailService.sendOtpEmail(email);
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        return "OTP sent to " + email;
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session) {
        String sessionOtp = (String) session.getAttribute("otp");
        //UserDetails tempUser = (UserDetails) session.getAttribute("tempUser");
        String email = (String) session.getAttribute("email");

        if (sessionOtp == null || email == null) {
            return "Session expired or invalid details.";
        }

        if (sessionOtp.equals(otp)) {
            userService.updateStatus(email, "VALIDATED");
            session.invalidate();
            return "OTP Verified and User registered successfully!";
        } else {
            return "Invalid OTP!";
        }
    }
}