package com.example.system.server.service;

import com.example.system.pojo.User;

import java.sql.SQLException;

public interface UserService {
    User findRecord(int Id);
    void save(User user);
    Boolean register(User user);
    int login(String count,String password);
    Boolean compare(String count);
    void savepw(User user);
}
