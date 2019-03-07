package com.example.system.server.mapper;

import com.example.system.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Mapper
@Repository
public interface UserMapper {
    User findRecord(int Id);
    void save(User user);
    void register(User user);
    User login(@Param("count") String count,@Param("password") String password);
    User compare(String count);
    void savepw(User user);
}
