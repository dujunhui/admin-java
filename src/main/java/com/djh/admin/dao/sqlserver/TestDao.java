package com.djh.admin.dao.sqlserver;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface TestDao {
    // 获取类别
    List<Map<String, Object>> getTest();

    Integer setDaili(@Param("id") Integer id, @Param("name") String name);

    Integer createTempByStr(@Param("tableName") String tableName);
}
