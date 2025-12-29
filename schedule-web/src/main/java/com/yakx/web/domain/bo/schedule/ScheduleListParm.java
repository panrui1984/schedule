package com.yakx.web.domain.bo.schedule;


import lombok.Data;

@Data
public class ScheduleListParm {
    private String roomIdList;

    private String stuId;
    //课程id列表
    private String courseIdList;
    //老师id列表
    private String teacherIdList;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //上课时间
    private String beginTime;
    //下课时间
    private String endTime;
}