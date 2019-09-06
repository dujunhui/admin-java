package com.djh.admin.dao.sqlserver2;

import com.djh.admin.model.Xsfpmx;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */
@Repository
public interface XsfpmxDao {


    List<Xsfpmx> getAllXsfpmx();
}
