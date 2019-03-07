package com.example.system.server;

import com.example.system.pojo.User;
import com.example.system.server.Dao.RedisDao;
import com.example.system.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @Autowired
    private RedisDao redisMethod;

    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    @RequestMapping("/test")
    public void hello(){
        user.setId(6);
        user.setRegister("华佗");
        userService.register(user);
        System.out.println(user.getRegister()==null);



    }
}
