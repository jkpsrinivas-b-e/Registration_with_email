package com.example.emailRegistration.controller;

import com.example.emailRegistration.repository.UserRepository;
import com.example.emailRegistration.service.EmailService;
import com.example.emailRegistration.entity.UserEntity;
import com.example.emailRegistration.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public String register(@RequestBody UserEntity user, HttpSession session) {
        //session.setAttribute("tempUser", user); stores in session
        userService.registerUser(user);

        String otp = emailService.sendOtpEmail(user.getEmail());

        session.setAttribute("otp", otp);
        session.setAttribute("email", user.getEmail());
        return "OTP sent to your email. Please verify it.";
    }



    @DeleteMapping("/delete-all")
    public String deleteAllUsers(){
        userRepository.deleteAll();
        return "All users katham";
    }
}