package com.djh.admin.controller;

import com.djh.admin.model.User;
import com.djh.admin.model.sys.CommonResult;
import com.djh.admin.service.UserService;
import com.djh.admin.uitls.CommonResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    //登录
    @RequestMapping("/login")
    public CommonResult login(@RequestBody Map apiData) {
        CommonResult commonResult = CommonResultFactory.success();
        Map<String, String> data = new HashMap<>();
        data.put("token","admin");
        commonResult.setData(data);
        return commonResult;
    }

    //获取user表数据
    @RequestMapping("/mysqlUsers")
    public CommonResult getMysqlUsers(){
        CommonResult commonResult = CommonResultFactory.success();
        List<User> userList = userService.getMysqlUsers();
        commonResult.setData(userList);
        return commonResult;
    }

    //登录后获取权限等信息
    @RequestMapping("/info")
    public CommonResult info(@RequestParam("token") String token) {
        CommonResult commonResult = CommonResultFactory.success();
        Map<String, Object> data = new HashMap<>();
        List<String> roles = new ArrayList<String>() ;
        roles.add("admin");
        data.put("roles",roles);
        data.put("name","admin");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        commonResult.setData(data);
        return commonResult;
    }

    //退出
    @RequestMapping("/logout")
    public CommonResult logout() {
        CommonResult commonResult = CommonResultFactory.success();

        commonResult.setData("");
        return commonResult;
    }
}
