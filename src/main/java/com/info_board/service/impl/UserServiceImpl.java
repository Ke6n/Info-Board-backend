package com.info_board.service.impl;

import com.info_board.mapper.UserMapper;
import com.info_board.pojo.User;
import com.info_board.service.UserService;
import com.info_board.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        // encrypt password
        String md5String = Md5Util.getMD5String(password);
        // add user
        userMapper.add(username, md5String);
    }
}
