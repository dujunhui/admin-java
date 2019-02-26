package com.djh.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/8/23.
 */
@Controller
@Slf4j
public class AdminController {

    @GetMapping("/Admin")
    public String index() {
        return "index";
    }

}
