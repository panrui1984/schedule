package com.yakx.web.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class SelectCourseBo {
    private Long stuCourseId;
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

    private Long teacherId;

    private String teacherName;
}
