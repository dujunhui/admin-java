package com.djh.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/8/24.
 */
@Data
@EqualsAndHashCode
public class SysUser {
    private Integer id;
    private String username;
    private String password;
}
