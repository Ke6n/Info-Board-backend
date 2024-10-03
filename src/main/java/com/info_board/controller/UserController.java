package com.info_board.controller;

import com.info_board.pojo.Result;
import com.info_board.pojo.User;
import com.info_board.service.UserService;
import com.info_board.utils.JwtUtil;
import com.info_board.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        // user query
        User user = userService.findByUserName(username);
        if (user == null){
            // create user
            userService.register(username, password);
            return Result.success();
        }else {
            return Result.error("The username is already in use!");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        // user query
        User loginUser = userService.findByUserName(username);
        // password verify
        if(loginUser != null && Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            // JWT-token generate
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        // Username or password is wrong
        return Result.error("Please enter the correct username or password!");
    }
}
