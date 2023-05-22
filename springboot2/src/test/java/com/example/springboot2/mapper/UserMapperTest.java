package com.example.springboot2.mapper;

import com.example.springboot2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void getAllUsers() {
        List<User> users = userMapper.getAllUsers();

        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void getUserById() {
        Long userId = 1L;
        User user = userMapper.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setName("Jane Smith");
        user.setAge(25);
        user.setEmail("jane@example.com");
        int res = userMapper.insertUser(user);
        assertEquals(1, res);
    }

    @Test
    public void updateUser() {
        User user = userMapper.getUserById(1L);
        user.setName("John Doe");

        int res = userMapper.updateUser(user);
        assertEquals(1, res);

        User updateUser = userMapper.getUserById(1L);
        assertEquals("John Doe", updateUser.getName());
    }

    @Test
    public void deleteUserById() {
        int res = userMapper.deleteUserById(6L);
        assertEquals(1, res);
    }
}