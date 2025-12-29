package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.web.domain.Course;
import com.yakx.web.domain.bo.CourseParmBo;
import com.yakx.web.domain.bo.SelectCourseBo;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper extends BaseMapper<Course> {
    IPage<Course> getSelectCourse(IPage<Course> page, @Param("parm") CourseParmBo parm);
    IPage<SelectCourseBo> getStuCourse(IPage<SelectCourseBo> page, @Param("parm") CourseParmBo parm);
    IPage<Course> getTeacherCourse(IPage<Course> page, @Param("parm") CourseParmBo parm);
}
