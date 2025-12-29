package com.yakx.web.domain.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReportVo {

    private Long id;
    //教室id
    private Long roomId;
    //教室名称
    private String roomName;
    //课程id
    private Long courseId;
    //课程名称
    private String courseName;
    //教师id
    private Long teacherId;
    //教师姓名
    private String teacherName;
    //日期
    private LocalDate dateTime;
    //上课时间
    private LocalTime beginTime;
    //下课时间
    private LocalTime endTime;
}
