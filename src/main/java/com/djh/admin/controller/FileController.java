package com.djh.admin.controller;

import com.djh.admin.model.sys.CommonResult;
import com.djh.admin.uitls.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
@RequestMapping("file")
public class FileController {

    @Autowired
    UploadUtil uploadUtil;

    // 上传图片
    @RequestMapping("upload")
    public CommonResult upload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        CommonResult commonResult = uploadUtil.saveFile(file, "works", 111);
        return  commonResult;
    }

}
