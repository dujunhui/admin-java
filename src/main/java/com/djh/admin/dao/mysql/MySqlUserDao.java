package com.djh.admin.dao.mysql;

import com.djh.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface MySqlUserDao {
    // 获取user表数据
    List<User> getMysqlUsers();

    // 设置user表里的name字段
    Integer setUserName(@Param("id") Integer id, @Param("name") String name);

    // 批量插入user表
    Integer batchInsert(List<User> list);
}
