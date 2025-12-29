package com.yakx.web.domain.vo;

import lombok.Data;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.system.TeacherRole;

import java.util.Date;

@Data
public class ProfileVo {

    private String userName;

    //工号
    private String userNum;

    //手机号
    private String phone;

    //邮箱
    private String email;

    private Date createTime;


    private Teacher teacher;

    private TeacherRole teacherRole;

}
