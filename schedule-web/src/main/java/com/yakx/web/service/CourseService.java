package com.yakx.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.Course;
import com.yakx.web.domain.bo.CourseParmBo;
import com.yakx.web.domain.bo.SelectCourseBo;

public interface CourseService extends IService<Course> {
    IPage<Course> getSelectCourse(CourseParmBo parm);
    IPage<SelectCourseBo> getStuCourse(CourseParmBo parm);
    IPage<Course> getTeacherCourse(CourseParmBo parm);
}
