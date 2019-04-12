package com.djh.admin.controller;

import com.djh.admin.model.sys.CommonResult;
import com.djh.admin.service.SysUserService;
import com.djh.admin.uitls.CommonResultFactory;
import com.djh.admin.uitls.JwtToken;
import com.djh.admin.uitls.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
@RequestMapping("login")
public class LoginController {

    @Value("${key.sysuserkey}")
    private String sysuserkey;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    HttpSession httpSession;

    //登录
    @RequestMapping("/login")
    public CommonResult login(@RequestBody Map apiData) {
        UUID uid = (UUID) httpSession.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        httpSession.setAttribute("uid", uid);


        CommonResult commonResult = CommonResultFactory.success();
        Map resMap = sysUserService.ifAdminLogin(apiData);
        if((Boolean) resMap.get("state")){
            //生成token
            Map<String, String> data = new HashMap<>();
            Long luid = new Long(Long.parseLong(resMap.get("id").toString()));
            String token;
            try {
                token = JwtToken.createToken(luid);
                String key = sysuserkey+luid.toString();
                //将token存入redis中
                redisUtil.set(key, token, 3600);
                data.put("token",token);
                commonResult.setData(data);
                commonResult.setMessage("登录成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            commonResult.setSuccess(false);
            commonResult.setMessage(String.valueOf(resMap.get("message")));
        }
        return commonResult;
    }

    //退出
    @RequestMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        CommonResult commonResult = CommonResultFactory.success();
        String token = request.getHeader("X-Token");
        Long appUID = JwtToken.getAppUID(token);
        //将token移除redis中
        redisUtil.del(sysuserkey + appUID.toString());
        commonResult.setData("");
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
}
