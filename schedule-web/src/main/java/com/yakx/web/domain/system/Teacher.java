package com.yakx.web.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {

    @TableId(type = IdType.AUTO)
    private Long teacherId;

    private String teacherName;

    @TableField(exist = false)
    private Long roleId;

    //教师编号
    private String teacherNum;

    //描述
    private String teacherDesc;

    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

}
