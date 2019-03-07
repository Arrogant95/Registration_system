package com.example.system.server.Impl;

import com.example.system.server.Dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

@Service("redisMethod")
public class RedisImpl implements RedisDao {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public int findRedis(String key) {
        String temp = jedisCluster.get(key);
        return Integer.parseInt(temp);
    }

    @Override
    public void init() {
        jedisCluster.set("xx","8");
    }

    @Override
    public Long decrease(String key) {
        return  jedisCluster.decr(key);
    }

    @Override
    public Long increase(String key) {
        return jedisCluster.incr(key);
    }
}
