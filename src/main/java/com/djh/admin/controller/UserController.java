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




    //获取user表数据
    @RequestMapping("/mysqlUsers")
    public CommonResult getMysqlUsers(){
        CommonResult commonResult = CommonResultFactory.success();
        List<User> userList = userService.getMysqlUsers();
        commonResult.setData(userList);
        return commonResult;
    }
}
