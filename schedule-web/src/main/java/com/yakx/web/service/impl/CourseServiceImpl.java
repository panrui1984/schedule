package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.Course;
import com.yakx.web.domain.bo.CourseParmBo;
import com.yakx.web.domain.bo.SelectCourseBo;
import com.yakx.web.mapper.CourseMapper;
import com.yakx.web.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Override
    public IPage<Course> getSelectCourse(CourseParmBo parm) {
        IPage<Course> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getSelectCourse(page,parm);
    }

    @Override
    public IPage<SelectCourseBo> getStuCourse(CourseParmBo parm) {
        IPage<SelectCourseBo> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getStuCourse(page,parm);
    }

    @Override
    public IPage<Course> getTeacherCourse(CourseParmBo parm) {
        IPage<Course> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getTeacherCourse(page,parm);
    }
}
