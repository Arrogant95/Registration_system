package com.example.system.server;

import com.example.system.pojo.User;
import com.example.system.server.Dao.Dao;
import com.example.system.server.Dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Controller
public class demo {
    @Autowired
    private Dao serverMethod;

    @Autowired
    private RedisDao redisMethod;

   @RequestMapping("/registerInterface")
    public String registerInterface(Map<String, Object> map){
//       redisMethod.init();
       int retain = redisMethod.findRedis("xx");
       map.put("retain",retain);
       return "registerInterface";
   }



    @RequestMapping("/reservation")
    public String reservation(){

        return "reservation";
    }

    @RequestMapping("/showRecord")
    @ResponseBody
    public User showRecord(int Id){
        User user = new User();
        user.setId(Id);
        user = serverMethod.findRecord(user.getId());
        return user;
    }

    @RequestMapping("/trySignIn")
    @ResponseBody
    public Integer trySignIn(String count,String password){
       User user = serverMethod.login(count,password);
       if (user != null){
       return user.getId();
       }else {
           return 0;
       }
    }

    @RequestMapping("/signIn")
    public String signIn(){
       return "signIn";
    }

    @RequestMapping("/trySignUp")
    @ResponseBody
    public Integer trySignUp(String count,String password,String name,String sex,String age,String Idcard,String workPlace,String fixedTelephone,String mobilePhone,String contactAddress){
        Boolean isexist = serverMethod.compare(count);
        if (isexist){
            return 0;
        }else {
            User user = new User();
            user.setCount(count);
            user.setPassword(password);
            user.setAge(age);
            user.setName(name);
            user.setSex(sex);
            user.setFixedTelephone(fixedTelephone);
            user.setIdCard(Idcard);
            user.setWorkPlace(workPlace);
            user.setMobilePhone(mobilePhone);
            user.setContactAddress(contactAddress);
            serverMethod.save(user);
            serverMethod.savepw(user);
            user = serverMethod.login(count,password);
            return user.getId();
        }

    }

    @RequestMapping("/tryRegister")
    @ResponseBody
    public String tryRegister(int Id ,String register,String key) throws Exception {
        User user = serverMethod.findRecord(Id);
       try {
           redisMethod.decrease(key);
           user.setId(Id);
           String temp = user.getRegister();
           temp = temp.equals("") ? temp+register:temp+","+register;
           user.setRegister(temp);
           Boolean issuccess = serverMethod.registered(user);
           if (issuccess){
               String temp0 = String.valueOf(redisMethod.findRedis(key));
               return temp+":"+temp0;
           }else {
               return "false";
           }
       }catch (Exception e){
            String temp0 = String.valueOf(redisMethod.increase(key));
            String temp = user.getRegister();
            return temp+":"+temp0;
       }


    }

    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }


}
