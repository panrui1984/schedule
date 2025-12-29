package com.yakx.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.bo.TeacherParmBo;

public interface TeacherService extends IService<Teacher> {
    IPage<Teacher> getList(TeacherParmBo parm);
    void addTeacher(Teacher teacher);
    void editTeacher(Teacher teacher);
    void deleteTeacher(Long teacherId);

    int resetTeacherPwd(Long teacherId, String password);
}
