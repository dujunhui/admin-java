package com.djh.admin.config.web;

import com.auth0.jwt.interfaces.Claim;
import com.djh.admin.uitls.JwtToken;
import com.djh.admin.uitls.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginHandler implements HandlerInterceptor {

    @Value("${key.sysuserkey}")
    private String sysuserkey;
    @Autowired
    RedisUtil redisUtil;

    //请求处理前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("X-Token");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(null==token){
            //没有token
            response.getWriter().write("{\"code\":\"50009\",\"message\":\"没有身份验证，请登陆\",\"data\":null}");
            response.getWriter().flush();
            return false;
        }
        if(!JwtToken.ifExp(token)){
            //token中的exp过期
            response.getWriter().write("{\"code\":\"50014\",\"message\":\"登录状态过期，请重新登录\",\"data\":null}");
            response.getWriter().flush();
            return false;
        }
        //根据key查看redis中的token是否相等
        Long sysUid = JwtToken.getAppUID(token);
        String rediesToken = String.valueOf(redisUtil.get(sysuserkey + sysUid.toString()));
        if(token.equals(rediesToken)){
            return true;
        }else{
            response.getWriter().write("{\"code\":\"50008\",\"message\":\"登录状态验证失败，请重新登录\",\"data\":null}");
            response.getWriter().flush();
            return false;
        }
    }

    //请求处理后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws                           Exception {
        System.out.println("拦截了");
    }

    //视图渲染之后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截了");
    }

}
