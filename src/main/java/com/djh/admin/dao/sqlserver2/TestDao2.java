package com.djh.admin.dao.sqlserver2;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface TestDao2 {

    Integer setDaili2(@Param("id") Integer id, @Param("name") String name);
}
