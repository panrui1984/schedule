package com.yakx.web.domain.vo.schedule;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yakx.common.convert.ExcelLocalTimeConvert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Long id;

    // 0 维护 1 实训
    private Integer type;


    //是否智能门锁，0 否， 1是
    private boolean isEzviz;


    //上课人数
    private Integer classNumber;

    private LocalDate dateTime;

    private String courseName;

    private String teacherName;

    private String className;

    private Long roomId;

    private String roomName;

    private String reason;
    //电子锁密码
    private String password;

    private Integer sequenceTotal;

    private LocalTime beginTime;

    private Long duration;

    private LocalTime endTime;

    private String remark;

    private String courseColor;

    private String roomAddress;
}
