package com.example.emailRegistration.service;

import com.example.emailRegistration.entity.UserEntity;
import com.example.emailRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserEntity registerUser(UserEntity user) {
        user.setStatus("PENDING");
        return userRepository.save(user);
    }


    public void updateStatus(String email, String status){
        Optional<UserEntity> userdetai = userRepository.findByEmail(email);
        if(userdetai.isPresent()){
            UserEntity user = userdetai.get();
            user.setStatus(status);
            userRepository.save(user);
        }
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
