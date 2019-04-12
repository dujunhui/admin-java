package com.djh.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

	@Autowired
	HttpSession httpSession;

	@Test
	public void contextLoads() {

	}

}
