package com.djh.admin.config.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by Administrator on 2018/5/2.
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {
    @Value("${file.localUploadPath}")
    private String localUploadPath;

    //拦截器使用@Autowired注入接口为null解决方法
    @Bean
    public HandlerInterceptor getLoginHandler(){
        return new LoginHandler();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginHandler())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login/**","/api/createTable","/EMS/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+localUploadPath+"upload\\");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
