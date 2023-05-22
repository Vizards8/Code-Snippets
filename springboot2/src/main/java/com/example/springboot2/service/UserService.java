package com.example.springboot2.service;

import com.example.springboot2.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUserById(Long userId);
}
