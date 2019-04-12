package com.djh.admin.service;


import com.djh.admin.dao.mysql.CategoryDao;
import com.djh.admin.dao.mysql.MySqlUserDao;
import com.djh.admin.dao.sqlserver.TestDao;
import com.djh.admin.dao.sqlserver2.TestDao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Service
public class TestService {

    @Autowired
    TestDao testDao;

    @Autowired
    TestDao2 testDao2;

    @Autowired
    MySqlUserDao mySqlUserDao;

    public List<Map<String, Object>> getTest(){ return testDao.getTest(); }


    @Transactional
    public Integer testShiWu(){
        Integer id = 5;
        String name = "0";
        mySqlUserDao.setUserName(id, name);
        testDao.setDaili(id, name);
        String a = null;
        a.toString();
        testDao2.setDaili2(id, name);
        return 1;
    }
}
