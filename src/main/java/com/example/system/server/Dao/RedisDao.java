package com.example.system.server;

public interface RedisDao {
    public int findRedis(String key);
    public void init();
    public Long decrease(String key);
    public Long increase(String key);
}
