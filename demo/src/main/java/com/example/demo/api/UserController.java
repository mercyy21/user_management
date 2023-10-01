package com.example.demo.api;

import com.example.demo.dao.UpdatePasswordRequest;
import com.example.demo.dao.UserDTO;
import com.example.demo.dao.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
    public ResponseEntity<UserDTO> registerPerson(@RequestBody UserEntity userEntity){

        return userService.registerUser(userEntity);

    }

    //login user
    @PostMapping("/login")
    public ResponseEntity<String> loginPerson(@RequestBody UserEntity userEntity){
        return userService.loginUser(userEntity.getEmail(),userEntity.getPassword());
    }
    //update users password
    @PutMapping("{id}")
    public ResponseEntity<String> updatePersonsPassword(@PathVariable Long id,@RequestBody UpdatePasswordRequest request){
        return userService.updateUsersPassword(id, request.getCurrentPassword(), request.getNewPassword());

    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutPerson(){
        return userService.logout();
    }



}
