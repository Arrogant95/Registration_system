package com.example.system.server;

import com.example.system.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public interface Dao {
    User findRecord(int Id);
    void save(User user);
    Boolean registered(User user) throws SQLException;
    User login(String count,String password);
    Boolean compare(String count);
    void savepw(User user);
}
