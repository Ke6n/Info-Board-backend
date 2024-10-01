package com.info_board.service;

import com.info_board.pojo.User;

public interface UserService {
    User findByUserName(String username);
    void register(String username, String password);
}
