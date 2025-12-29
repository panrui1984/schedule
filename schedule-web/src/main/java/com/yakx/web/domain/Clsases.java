package com.yakx.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("clsases")
@Data
public class Clsases {

    @TableId(type = IdType.AUTO)
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级人数
     */
    private Integer classNumber;

    /**
     * 大队
     */
    private String dadui;

    /*
     * 描述
     */
    private String calassDesc;
}
