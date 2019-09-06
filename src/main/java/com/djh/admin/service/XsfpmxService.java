package com.djh.admin.service;


import com.djh.admin.dao.sqlserver2.XsfpmxDao;
import com.djh.admin.model.Xsfpmx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */
@Service
public class XsfpmxService {



    @Autowired
    XsfpmxDao xsfpmxDao;


    public List<Xsfpmx> getAllXsfpmx(){
        return xsfpmxDao.getAllXsfpmx();
    }


}
