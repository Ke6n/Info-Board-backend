package com.info_board.controller;

import com.info_board.pojo.Result;
import com.info_board.pojo.User;
import com.info_board.service.UserService;
import com.info_board.utils.JwtUtil;
import com.info_board.utils.Md5Util;
import com.info_board.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
            valueOps.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }
        // Username or password is wrong
        return Result.error("Please enter the correct username or password!");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<User> update(@RequestBody @Validated User user){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        if (user.getId().equals(id)) {
            userService.update(user);
            return Result.success();
        } else {
            return  Result.error("Illegal operation: User ID has been tampered with!");
        }
    }

    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token){
        // Parameter Validation
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("Missing required parameters!");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if(!rePwd.equals(newPwd)){
            return Result.error("The new passwords filled in twice are inconsistent!");
        }
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("Please enter the correct original password!");
        }
        // Update password
        userService.updatePwd(newPwd);
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.getOperations().delete(token);
        return Result.success();
    }
}
