package com.yakx.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data //自动生成get和set方法
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long courseId;
    //课程名称
    private String courseName;
    //课程类型 0：春季 1：秋季
    private String courseType;
    //学年
    private String courseYear;
    //创建时间
    private Date courseTime;
    //课程颜色
    private String courseColor;

    @TableField(exist = false)
    private Long teacherId;

    @TableField(exist = false)
    private String teacherName;
}
