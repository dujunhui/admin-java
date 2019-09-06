package com.djh.admin.controller;

import com.djh.admin.service.EMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.djh.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    EMSService emsService;

    @RequestMapping("/")
    public String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping("hello")
    public String  sayHello(){
        return "index";
    }

    @RequestMapping("bye")
    public String sayBye(){
        log.info("goodBye!!!再见");
        log.debug("debug message");
        log.warn("warn message");
        log.info("info message");
        log.error("error message");
        log.trace("trace message");
        return "bye";
    }

    //接收json对象
//    @RequestMapping(value = "/setMysqlUsers", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @RequestMapping("/setMysqlUsers")
    @PostMapping("/setMysqlUsers")
    public String setMysqlUsers(@RequestBody Map apiData){
        Integer id = (Integer) apiData.get("id");
        String name = (String) apiData.get("name");
        if(userService.setUserName(id, name).equals(1)){
            return "ok";
        }else{
            return "error";
        }
    }

    //接收参数
    @RequestMapping(value = "/testParam")
    public String testParam(@RequestParam("id") Integer id){
        System.out.print(id);
        return id.toString();
//        if(userService.setUserName().equals(1)){
//            return "ok";
//        }else{
//            return "error";
//        }
    }

}
