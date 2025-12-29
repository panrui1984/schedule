package com.yakx.web.domain.schedule;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.yakx.common.convert.ExcelLocalTimeConvert;
import com.yakx.common.core.domain.BaseEntity;
import com.yakx.web.domain.vo.schedule.Reservation;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 教室预约
 * @author 26166
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@TableName("classroom_reserve")
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomReserve extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    //教室id
    private Long roomId;

    //是否智能门锁，0 否， 1是
    private Integer isEzviz;

    //课程id
    private Long courseId;
    //教师id
    private Long teacherId;
    //班级编码
    private Long classId;
    //上课人数
    private Integer classNumber;

    @ExcelProperty(value = "日期")
    @OrderBy()
    private LocalDate dateTime;

    @ExcelProperty(value = "实训项目")
    @TableField(exist = false)
    private String courseName;

    @ExcelProperty(value = "教师")
    @TableField(exist = false)
    private String teacherName;

    @ExcelProperty(value = "班级")
    @TableField(exist = false)
    private String className;

    @ExcelProperty(value = "实验室")
    @TableField(exist = false)
    private String roomName;

    @ExcelProperty(value = "使用目的")
    private String reason;
    //电子锁密码
    private String password;

    @ExcelProperty(value = "课时")
    private Integer sequenceTotal;

    @ExcelProperty(value = "上课时间", converter = ExcelLocalTimeConvert.class)
    private LocalTime beginTime;
    //课程时长
    private Long duration;

    @ExcelProperty(value = "下课时间", converter = ExcelLocalTimeConvert.class)
    private LocalTime endTime;

    @ExcelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    private String courseColor;

    @TableField(exist = false)
    private String roomAddress;
}
