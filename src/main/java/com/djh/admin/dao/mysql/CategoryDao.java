package com.djh.admin.dao.mysql;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface CategoryDao {
    // 获取类别
    List<Map<String, Object>> getCategory();
}
