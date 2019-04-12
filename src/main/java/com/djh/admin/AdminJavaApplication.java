package com.djh.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement

// maxInactiveIntervalInSeconds: 设置 Session 失效时间
// 使用 Redis Session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效。
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)

public class AdminJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminJavaApplication.class, args);
	}
}
