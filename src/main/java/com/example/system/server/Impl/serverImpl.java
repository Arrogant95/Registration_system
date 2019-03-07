package com.example.system.server.Impl;

import com.example.system.pojo.User;
import com.example.system.server.Dao.Dao;
import com.example.system.server.Dao.RedisDao;
import com.example.system.server.Dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@Service("serverMethod")
public class serverImpl implements Dao {

    @Autowired
    public JdbcTemplate jt;

    @Autowired
    private RedisDao redisMethod;

    @Override
    public User findRecord(int Id) {
        String sql = "select * from user where Id = ?";
        return jt.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User u = new User();
                u.setContactAddress(resultSet.getString("contactAddress"));
                u.setName(resultSet.getString("name"));
                u.setSex(resultSet.getString("sex"));
                u.setAge(resultSet.getString("age"));
                u.setIdCard(resultSet.getString("IdCard"));
                u.setWorkPlace(resultSet.getString("workPlace"));
                u.setFixedTelephone(resultSet.getString("fixedTelephone"));
                u.setMobilePhone(resultSet.getString("mobilePhone"));
                u.setRegister(resultSet.getString("register"));
                return u;
            }
        },Id);

    }


    @Override
    @Transactional
    public void save(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,?,?,?)";
        jt.update(sql,user.getName(),user.getSex(),user.getAge(),user.getIdCard(),user.getWorkPlace()
        ,user.getFixedTelephone(),user.getMobilePhone(),user.getContactAddress()," ");
    }

    @Override
    @Transactional
    public Boolean registered(User user) throws SQLException {
        String sql = "update user set register=? where id=?";
        int row = jt.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,user.getRegister().toString());
                preparedStatement.setInt(2,user.getId());
            }
        });
        throw new RuntimeException("发生异常了..");
//        return true;

    }

    @Override
    public User login(String count, String password) {

        String sql = "select * from count where usercount=? and password=?";
        try {
            User user = jt.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User u = new User();
                    u.setId(resultSet.getInt("Id"));
                    return u;
                }
            },count,password);
            return user;
        }catch (EmptyResultDataAccessException e){
            return null;
        }


    }

    @Override
    public Boolean compare(String count) {
        String sql = "select * from count where usercount = ?";
        try {
            User user = jt.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return null;
                }
            },count);
        }catch (EmptyResultDataAccessException e){
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public void savepw(User user) {
        String sql = "insert into count values(null,?,?)";
        jt.update(sql,user.getCount(),user.getPassword());
    }


}
