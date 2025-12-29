package com.yakx.web.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Long stuId;
    private Long classId;
    @TableField(exist = false)
    private Long roleId;
    private String stuName;
    private String sex;
    private String phone;
    private String stuNum;
    private String password;
    @TableField(exist = false)
    private String className;
}
