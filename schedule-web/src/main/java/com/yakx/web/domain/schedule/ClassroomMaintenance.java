package com.yakx.web.domain.schedule;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.yakx.common.convert.ExcelLocalTimeConvert;
import com.yakx.common.core.domain.BaseEntity;
import com.yakx.web.domain.vo.schedule.Reservation;
import io.github.linpeilie.annotations.AutoMapMapper;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author 26166
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@TableName("classroom_maintenance")
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomMaintenance extends BaseEntity {

    @TableId()
    private Long id;

    //教室id
    private Long roomId;

    @ExcelProperty(value = "日期")
    @OrderBy()
    private LocalDate dateTime;

    @ExcelProperty(value = "实验室")
    @TableField(exist = false)
    private String roomName;

    @ExcelProperty(value = "上课时间", converter = ExcelLocalTimeConvert.class)
    private LocalTime beginTime;

    @ExcelProperty(value = "下课时间", converter = ExcelLocalTimeConvert.class)
    private LocalTime endTime;

    @ExcelProperty(value = "实验室地址")
    @TableField(exist = false)
    private String roomAddress;
}
