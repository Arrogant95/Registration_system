package com.example.system.server;

import com.example.system.pojo.User;
import com.example.system.server.Dao.RedisDao;
import com.example.system.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class demo {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisDao redisMethod;

    @Autowired
    private  User user;

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
        return userService.findRecord(Id);
    }

    @RequestMapping("/trySignIn")
    @ResponseBody
    public Integer trySignIn(String count,String password){
       return userService.login(count,password);
    }

    @RequestMapping("/signIn")
    public String signIn(){
       return "signIn";
    }

    @RequestMapping("/trySignUp")
    @ResponseBody
    public Integer trySignUp(String count,String password,String name,String sex,String age,String Idcard,String workPlace,String fixedTelephone,String mobilePhone,String contactAddress){
        Boolean isexist = userService.compare(count);
        if (isexist){
            return 0;
        }else {
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
            userService.save(user);
            userService.savepw(user);
            user.setId(userService.login(count,password));
            return user.getId();
        }

    }

    @RequestMapping("/tryRegister")
    @ResponseBody
    public String tryRegister(int Id ,String register,String key) throws Exception {
        User user = userService.findRecord(Id);
       try {
           redisMethod.decrease(key);
           user.setId(Id);
           String temp = user.getRegister();
           temp = temp==null ? register:temp+","+register;
           user.setRegister(temp);
           Boolean issuccess = userService.register(user);
           if (issuccess){
               String temp0 = String.valueOf(redisMethod.findRedis(key));
               System.out.println(temp0);
               return temp+":"+temp0;
           }else {
               return "false";
           }
       }catch (Exception e){
            String temp0 = String.valueOf(redisMethod.increase(key));
            String temp = user.getRegister();
            return "false";
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
