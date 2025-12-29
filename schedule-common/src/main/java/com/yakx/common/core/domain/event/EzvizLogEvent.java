package com.yakx.common.core.domain.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class EzvizLogEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String module = "萤石智能锁";

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
}
