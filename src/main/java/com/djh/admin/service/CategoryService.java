package com.djh.admin.service;


import com.djh.admin.dao.mysql.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<Map<String, Object>> getCagegory(){ return categoryDao.getCategory(); }
}
