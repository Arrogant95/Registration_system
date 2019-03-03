package com.example.system.server;

import com.example.system.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {
    @Autowired
    private Dao serverMethod;

    @Autowired
    private RedisDao redisMethod;

    @RequestMapping("/test")
    public Long hello(){
        return redisMethod.decrease("xx");
    }
}
