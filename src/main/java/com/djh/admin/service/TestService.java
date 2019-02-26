package com.djh.admin.service;


import com.djh.admin.dao.mysql.CategoryDao;
import com.djh.admin.dao.mysql.MySqlUserDao;
import com.djh.admin.dao.sqlserver.TestDao;
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
    MySqlUserDao mySqlUserDao;

    public List<Map<String, Object>> getTest(){ return testDao.getTest(); }


    @Transactional
    public Integer testShiWu(){
        Integer id = 5;
        String name = null;
        mySqlUserDao.setUserName(id, name);
        testDao.setDaili(id, name);
        return 1;
    }
}
