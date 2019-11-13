package com.djh.admin.controller;

import com.djh.admin.model.sys.CommonResult;
import com.djh.admin.service.CategoryService;
import com.djh.admin.service.TestService;
import com.djh.admin.uitls.CommonResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/23.
 */
@Controller
@Slf4j
@RequestMapping("api")
public class ApiController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    TestService testService;

    @GetMapping("/uiList")
    @ResponseBody
    public CommonResult uiList() {
        CommonResult commonResult = CommonResultFactory.success();
        return commonResult;
    }

    @GetMapping("/createTable")
    @ResponseBody
    public CommonResult createTable(){
        CommonResult commonResult = CommonResultFactory.success();
        Integer person = testService.createTempByStr("##person");
        return commonResult;
    }

    //获取类别
    @GetMapping("/getCategory")
    @ResponseBody
    public CommonResult getCategory() {
        CommonResult commonResult = CommonResultFactory.success();
        List<Map<String, Object>> categorys = categoryService.getCagegory();
        commonResult.setData(categorys);
        return commonResult;
    }

    //获取类别
    @GetMapping("/getTest")
    @ResponseBody
    public CommonResult getTest() {
        CommonResult commonResult = CommonResultFactory.success();
        List<Map<String, Object>> tests = testService.getTest();
        commonResult.setData(tests);
        return commonResult;
    }

    //测试分布式事务回滚
    @GetMapping("/testShiWu")
    @ResponseBody
    public CommonResult testShiWu() {
        CommonResult commonResult = CommonResultFactory.success();
        testService.testShiWu();
        return commonResult;
    }

}
