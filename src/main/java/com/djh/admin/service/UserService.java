package com.djh.admin.service;


import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.djh.admin.dao.mysql.MySqlUserDao;
import com.djh.admin.model.User;
import com.djh.admin.uitls.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    MySqlUserDao mySqlUserDao;

    // 获取user表数据
    public List<User> getMysqlUsers(){ return mySqlUserDao.getMysqlUsers(); }

    public Integer setUserName(Integer id, String name){
        return mySqlUserDao.setUserName(id, name);
    }

    // 导出excel
    public void exportExcel(HttpServletResponse response){
        List<User> list = getMysqlUsers();
        // 获取导出excel指定模版
        TemplateExportParams params = new TemplateExportParams();
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        params.setHeadingRows(2);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        params.setSheetName("用户信息");

        params.setHeadingRows(2);
        params.setHeadingStartRow(2);
        params.setTempParams("t");
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("list", list);
        try {
            Workbook workbook = ExcelUtil.getWorkbook(params, data, "example.xls");
            //下载
            ExcelUtil.export(response, workbook, "经销商用户信息");
        } catch (Exception e) {
            log.error("error",e);
        }
    }

    @Transactional
    public Boolean insertUsers(List<List<String>> datas) {
        Boolean res = true;
        try {
            for (int i = 0; i < datas.size(); i++) {
                String[] str = datas.get(i).toArray(new String[0]);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            res = false;
            throw new RuntimeException(e.getMessage()); //在这儿抛异常，事务回滚
        }
        return res;
    }
}
