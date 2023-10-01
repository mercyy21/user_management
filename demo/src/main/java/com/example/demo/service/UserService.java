package com.example.demo.service;

import com.example.demo.dao.UserDTO;
import com.example.demo.dao.UserEntity;
import com.example.demo.dao.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private UserRepository userRepository;

@Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    //Register User
    public ResponseEntity<UserDTO> registerUser(@NotNull UserEntity userEntity){

    //Hashes the password
    String salt = BCrypt.gensalt();//generates the salt
    String hashedPassword= BCrypt.hashpw(userEntity.getPassword(),salt);
    userEntity.setSalt(salt);
    userEntity.setPassword(hashedPassword);

    //Checks if the email or phone number already exist
    if(userRepository.existsByEmail(userEntity.getEmail()) || userRepository.existsByPhoneNumber(userEntity.getPhoneNumber())){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    UserEntity savedUser = userRepository.save(userEntity);

    //Map Saved user to User DTO
    //To customize what appears on the Json Request
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setName(savedUser.getName());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setPhoneNumber(savedUser.getPhoneNumber());
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(userDTO);
    }

    //login user
    public ResponseEntity<String> loginUser(String email,String password) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            if (BCrypt.checkpw(password, userEntity.getPassword())) {
                userEntity.setLastLoginTime(new Date());
                userRepository.save(userEntity);
                return new ResponseEntity<>(" Login Successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("Incorrect Password",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        }

    //update users password
    public ResponseEntity<String> updateUsersPassword(Long id,String currentPassword, String newPassword){
    UserEntity userEntity = userRepository.findById(id).orElse(null);
    if(userEntity != null){
        if(BCrypt.checkpw(currentPassword,userEntity.getPassword())){
            String salt = BCrypt.gensalt();//generates the salt
            String hashedPassword= BCrypt.hashpw(newPassword,salt);
            userEntity.setSalt(salt);
            userEntity.setPassword(hashedPassword);
            userRepository.save(userEntity);
            return ResponseEntity.ok("Successful");
        }
        return new ResponseEntity<>("Current password incorrect",HttpStatus.BAD_REQUEST);
    }
    else {
        return new ResponseEntity<>("User doesn't exist",HttpStatus.NOT_FOUND);
    }

    }
    //Logout
    public ResponseEntity<String> logout(){
    return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    }





