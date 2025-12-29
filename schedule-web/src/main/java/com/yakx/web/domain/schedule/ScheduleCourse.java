package com.yakx.web.domain.schedule;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yakx.common.core.domain.BaseEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("schedule_course")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCourse extends BaseEntity {

    @TableId()
    private Long id;

    //类型
    //1 课程 0 维护
    private Integer type;
    //教室id
    private Long roomId;
    //课程id
    private Long courseId;
    //教师id
    private Long teacherId;
    //班级编码
    private Long classId;

    //上课人数
    private Integer classNumber;
    //使用目的
    private String reason;

    //电子锁密码
    private String password;

    //日期
    private LocalDate dateTime;
    //课程序号
    private Integer sequence;
    //上课时间
    private LocalTime beginTime;
    //课程时长
    private Long duration;
    //下课时间
    private LocalTime endTime;

    //预约编码
    @TableField("reserve_id")
    private Long reserveId;
    //备注
    private String remark;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String courseColor;
    @TableField(exist = false)
    private String roomAddress;
}
