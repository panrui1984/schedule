package com.yakx.web.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher_role")
public class TeacherRole {
    @TableId(type = IdType.AUTO)
    private Long teacherRoleId;
    private Long teacherId;
    private Long roleId;
}
