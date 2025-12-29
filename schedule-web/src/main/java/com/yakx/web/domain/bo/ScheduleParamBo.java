package com.yakx.web.domain.bo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ScheduleParamBo {

    //教室id
    private Long roomId;

    //课程id
    private Long courseId;

    //教师id
    private Long teacherId;

    //班级编码
    private Long classId;

    //上课时间
    private LocalTime beginTime;

    //下课时间
    private LocalTime endTime;

    //开课日期
    private LocalDate startDate;

    //结课日期
    private LocalDate endDate;

    //每天课程序列
    private List<Integer> sequences;

    //周几上课列表
    private List<Integer> weeks;

    //上课人数
    private Integer classNumber;

    private Long duration;

    private String reason;

    private String remark;

    private LocalDate date_time;

}