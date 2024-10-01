package com.info_board.controller;

import com.info_board.pojo.Result;
import com.info_board.pojo.User;
import com.info_board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password){
        // user query
        User user = userService.findByUserName(username);
        if (user == null){
            userService.register(username, password);
            return Result.success();
        }else {
            return Result.error("The username is already in use!");
        }
        // create user

    }
}
