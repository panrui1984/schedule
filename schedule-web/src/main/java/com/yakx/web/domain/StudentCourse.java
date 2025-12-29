package com.yakx.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_course")
public class StudentCourse {
    @TableId(type = IdType.AUTO)
    private Long stuCourseId;
    private Long stuId;
    private Long courseId;
    private Long teacherId;
}
