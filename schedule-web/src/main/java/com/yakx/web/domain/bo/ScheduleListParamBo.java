package com.yakx.web.domain.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleListParamBo {

    //教室id
    private List<Long> roomIdList;

    //课程id
    private List<Long> courseIdList;

    //教师id
    private List<Long> teacherIdList;

    private String date;

}
