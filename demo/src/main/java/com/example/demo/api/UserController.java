package com.example.demo.api;

import com.example.demo.dao.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Register user
    @PostMapping
    public ResponseEntity<UserEntity> registerPerson(@RequestBody UserEntity userEntity){
        return userService.registerUser(userEntity);

    }

    //login user
    @PostMapping("/login")
    public ResponseEntity<String> loginPerson(@RequestBody UserEntity userEntity){
        return userService.loginUser(userEntity.getEmail(),userEntity.getPassword());
    }

}
