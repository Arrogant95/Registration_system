package com.example.system.server.Impl;

import com.example.system.pojo.User;
import com.example.system.server.mapper.UserMapper;
import com.example.system.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class mybatisImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User user;

    @Override
    public User findRecord(int Id) {
        user.setId(Id);
        user = userMapper.findRecord(user.getId());
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    @Transactional
    public Boolean register(User user) {
        userMapper.register(user);
        throw new RuntimeException("出错了");
//        return true;
    }

    @Override
    public int login(String count, String password) {
        user = userMapper.login(count,password);
        if (user != null){
            return user.getId();
        }else {
            return 0;
        }
    }

    @Override
    public Boolean compare(String count) {
        if (userMapper.compare(count) != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void savepw(User user) {
        userMapper.savepw(user);
    }


}
