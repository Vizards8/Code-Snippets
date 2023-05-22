package com.example.springboot2.mapper;

import com.example.springboot2.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getAllUsers();
    User getUserById(Long userId);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUserById(Long userId);
}
