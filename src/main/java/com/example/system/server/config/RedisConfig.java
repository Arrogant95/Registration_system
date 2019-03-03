package com.example.system.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
@Configuration
public class RedisConfig {
    @Value("${spring.redis.cluster.nodes}")
    private String culsterNodes;

    @Bean
    public JedisCluster getJedisCluster(){
        String[] cNodes = culsterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String node: cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(nodes);
        return jedisCluster;
    }
}
