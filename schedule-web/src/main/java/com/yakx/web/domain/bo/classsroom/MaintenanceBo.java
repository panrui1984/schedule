package com.yakx.web.domain.bo.classsroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceBo {

    private Long id;

    //教室id
    private Long roomId;

    private List<Integer> roomIds;

    private LocalDate dateTime;

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

}
