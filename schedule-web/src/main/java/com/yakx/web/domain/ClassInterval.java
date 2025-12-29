package com.yakx.web.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author yakx
 */
@Data
public class ClassInterval {

    private Integer id;

    private Integer sequence;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    @TableField(exist = false)
    private LocalTime startTime;

    @TableField(exist = false)
    private LocalTime endTime;

}
