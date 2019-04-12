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
 * 商品类别
 */
@Controller
@Slf4j
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //获取类别树
    @GetMapping("/getCategory")
    @ResponseBody
    public CommonResult getCategory() {
        CommonResult commonResult = CommonResultFactory.success();
        List<Map<String, Object>> categorys = categoryService.getCagegory();
        commonResult.setData(categorys);
        return commonResult;
    }


}
