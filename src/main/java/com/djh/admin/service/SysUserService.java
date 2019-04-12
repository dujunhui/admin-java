package com.djh.admin.service;


import com.djh.admin.dao.mysql.SysUserDao;
import com.djh.admin.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Slf4j
@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    //判断密码是否正确并登录
    public Map ifAdminLogin(Map loginMap){
        Map<String, Object> resultMap = new HashMap<>();
        SysUser sysUser = sysUserDao.getSysUserByName(String.valueOf(loginMap.get("username")));
        if (null==sysUser) {
            //没有该用户
            resultMap.put("state", false);
            resultMap.put("message", "没有该用户");
            return resultMap;
        }
        String password = sysUser.getPassword();
        if(!password.equals(loginMap.get("password"))){
            //密码不正确
            resultMap.put("state", false);
            resultMap.put("message", "密码不正确");
            return resultMap;
        }
        resultMap.put("state", true);
        resultMap.put("id", sysUser.getId());
        resultMap.put("message", "登录成功");
        return resultMap;
    }

}
