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
public class Xsfpmx {
    private String djnm;//单据内码
    private String cpdm;//产品代码
    private String cpmc;//产品名称
    private String num;//数量
    private String xh;//型号
}
