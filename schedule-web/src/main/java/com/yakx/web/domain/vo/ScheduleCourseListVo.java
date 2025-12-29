package com.yakx.web.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yakx.common.convert.ExcelLocalTimeConvert;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@ExcelIgnoreUnannotated
public class ScheduleCourseListVo {
    private Long id;
    //教室id
    private Long roomId;

    @ExcelProperty(value = "实验室")
    //教室名称
    private String roomName;
    //课程id
    private Long courseId;
    //课程名称
    @ExcelProperty(value = "实训项目")
    private String courseName;
    //课程背景颜色
    private String courseColor;
    //教师id
    private Long teacherId;
    //教师姓名
    @ExcelProperty(value = "教师")
    private String teacherName;
    //班级id
    private Long classId;
    @ExcelProperty(value = "班级")
    //班级名称
    private String className;
    @ExcelProperty(value = "日期")
    //日期
    private LocalDate dateTime;
    @ExcelProperty(value = "课程序列")
    private Integer sequence;

    private String password;
 //   @ExcelProperty(value = "上课时间")
    //上课时间
    private LocalTime beginTime;
 //   @ExcelProperty(value = "下课时间", converter = ExcelLocalTimeConvert.class))
    //下课时间
    private LocalTime endTime;

    @ExcelProperty(value = "预约人")
    private String createBy;
    @ExcelProperty(value = "预约时间")
    private Date createTime;

    private Long reserveId;

}
