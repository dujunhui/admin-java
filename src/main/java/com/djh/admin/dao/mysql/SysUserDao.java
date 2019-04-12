package com.djh.admin.dao.mysql;

import com.djh.admin.model.SysUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface SysUserDao {
    // 根据用户名获取password
    SysUser getSysUserByName(String username);
}
