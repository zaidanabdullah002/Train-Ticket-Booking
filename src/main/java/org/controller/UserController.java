package org.controller;

import org.entities.User;
import org.services.UserService;

import java.util.List;


public class UserController {
    UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    public User signup(String userName,String password){
        return userService.signUp(userName,password);
    }
    public User login(String userName,String password){
        return userService.login(userName,password);
    }
}
