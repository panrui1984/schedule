package com.yakx.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ezviz_log")
@ExcelIgnoreUnannotated
public class EzvizLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 操作模块
     */
    private String module;


    /**
     * 业务类型（0其它 1新增 2修改 3删除 4查询）
     */
    private String businessType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 设备编码
     */
    private String deviceSerial;

    /**
     * 返回编码
     */
    private String code;

    /**
     * 返回日志
     */
    private String message;
    /**
     * 操作时间
     */
    private Date operTime;

}
