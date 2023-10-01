package com.example.demo.dao;

public class UserDTO {
    //Custom response
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;


//Reminder: the UserDTO didn't work because I didn't include getters. Getters are important when trying to print
//something out to the body
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
