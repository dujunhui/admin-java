package com.djh.admin.controller;

import com.djh.admin.model.sys.CommonResult;
import com.djh.admin.service.UserService;
import com.djh.admin.uitls.CommonResultFactory;
import com.djh.admin.uitls.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@Slf4j
@RequestMapping("Excel")
public class ExcelController {

    @Autowired
    UserService userService;

    /**
     * 导出
     * user表导出excel文件
     * 导出模板文件放在templates/example.xls
     */
    @RequestMapping( "/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response){
        userService.exportExcel(response);
    }

    /**
     * 导入
     * excel数据导入到user表中
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult importExcel(@RequestParam(value = "excelFile", required = false) MultipartFile file, HttpServletRequest request) {
        CommonResult commonResult = CommonResultFactory.failed();
        try {
            MultipartRequest multipartRequest = (MultipartRequest) request;
            MultipartFile excelFile = multipartRequest.getFile("excelFile");
            if(excelFile!=null){
                String filename = excelFile.getOriginalFilename();
                String suffix = filename.substring(filename.lastIndexOf("."),filename.length());
                List<List<String>> datas = new ArrayList<List<String>>();
                if(suffix.equals(".xlsx")){
                    datas = ExcelUtil.readXlsx(excelFile.getInputStream());
                }else if (suffix.equals(".xls")){
                    datas = ExcelUtil.readXls(excelFile.getInputStream());
                }
                // 读到的数据都在datas里面，根据实际业务逻辑做相应处理
                if(datas!=null && datas.size()>0){
                    Boolean res = userService.insertUsers(datas);
                    if(res){
                        // 导入成功
                        commonResult.setSuccess(true);
                    }else{
                        commonResult.setMessage("失败");
                    }
                    return commonResult;
                }else{
                    commonResult.setMessage("excel文件数据为空");
                    return commonResult;
                }
            }else{
                commonResult.setMessage("没有找到excel文件");
                return commonResult;
            }
        } catch (Exception e) {
            commonResult.setMessage(e.getMessage());
            return commonResult;
        }
    }
}
