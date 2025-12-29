package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.StudentCourse;
import com.yakx.web.mapper.StudentCourseMapper;
import com.yakx.web.service.StudentCourseService;
import org.springframework.stereotype.Service;

/**
 * @author yakx
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {
}
