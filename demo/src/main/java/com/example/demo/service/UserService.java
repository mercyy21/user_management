package com.example.demo.service;

import com.example.demo.dao.UserEntity;
import com.example.demo.dao.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService {
    private UserRepository userRepository;

@Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    //Register User
    public ResponseEntity<UserEntity> registerUser(@NotNull UserEntity userEntity){
    String salt = BCrypt.gensalt();//generates the salt
    String hashedPassword= BCrypt.hashpw(userEntity.getPassword(),salt);
    userEntity.setSalt(salt);
    userEntity.setPassword(hashedPassword);
    if(userRepository.existsByEmail(userEntity.getEmail()) || userRepository.existsByPhoneNumber(userEntity.getPhoneNumber())){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    UserEntity savedUser = userRepository.save(userEntity);
    return ResponseEntity.ok(savedUser);
    }

    //Update last time logged in
    /*public void updateLastLoginTime(UserEntity userEntity){
    LocalDateTime localDateTime = LocalDateTime.now();
    UserEntity userEntity1 = userRepository.findByEmail(userEntity.getEmail());
    userEntity.setLastLoginTime(localDateTime);
    }*/

    //login user
    public ResponseEntity<String> loginUser(String email,String password) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity != null) {
            userEntity.setLastLoginTime(new Date());
            if (BCrypt.checkpw(password, userEntity.getPassword())) {
                return new ResponseEntity<>(" Login Successful", HttpStatus.OK);

            }

            return new ResponseEntity<>("Unsuccessful",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        } //Create an update last time logged in function

    //update users password
    public ResponseEntity<String> updateUsersPassword(Long id,String currentPassword, String newPassword){
    UserEntity userEntity = userRepository.findById(id).orElse(null);
    if(userEntity != null){
        if(BCrypt.checkpw(currentPassword,userEntity.getSalt())){
            String salt = BCrypt.gensalt();//generates the salt
            String hashedPassword= BCrypt.hashpw(newPassword,salt);
            userEntity.setSalt(salt);
            userEntity.setPassword(hashedPassword);
            return ResponseEntity.ok("Successful");
        }
        return new ResponseEntity<>("Current password incorrect",HttpStatus.BAD_REQUEST);
    }
    else {
        return new ResponseEntity<>("User doesn't exist",HttpStatus.NOT_FOUND);
    }

    }

    }




