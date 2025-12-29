package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yakx.common.core.domain.PageQuery;
import com.yakx.common.core.domain.R;
import com.yakx.common.utils.StringUtils;
import com.yakx.common.utils.ResultUtils;
import com.yakx.common.utils.ResultVo;
import com.yakx.web.domain.Course;
import com.yakx.web.domain.bo.CourseParmBo;
import com.yakx.web.domain.bo.SelectCourseBo;
import com.yakx.web.domain.vo.SelectOptionVo;
import com.yakx.web.service.CourseService;
import com.yakx.web.domain.StudentCourse;
import com.yakx.web.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    private final StudentCourseService studentCourseService;

    //新增课程
    @PostMapping
    public R<Void> add(@RequestBody Course course) {
        course.setCourseTime(new Date());
        boolean save = courseService.save(course);
        if(save){
            return R.ok("新增成功!");
        }
        return R.fail("新增失败!");
    }

    //编辑课程
    @PutMapping
    public R<Void> edit(@RequestBody Course course) {
        course.setCourseTime(new Date());
        boolean save = courseService.updateById(course);
        if(save){
            return R.ok("编辑成功!");
        }
        return R.fail("编辑失败!");
    }

    //删除
    @DeleteMapping("/{courseId}")
    public R<Void> delete(@PathVariable("courseId") Long courseId){
        boolean save = courseService.removeById(courseId);
        if(save){
            return R.ok("删除成功!");
        }
        return R.fail("删除失败!");
    }


    @GetMapping("/list")
    public R<IPage<Course>> getList(CourseParmBo bo, PageQuery pageQuery){
        IPage<Course> list = courseService.page(pageQuery.build(),  this.buildQueryWrapper(bo));
        return R.ok("查询成功",list);
    }

    //课程
    @GetMapping("/getCourseList")
    public R<List<SelectOptionVo>> getCourseList(){
        List<Course> list = courseService.list();
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
            .forEach(item->{
                SelectOptionVo option = new SelectOptionVo();
                option.setValue(item.getCourseId());
                option.setLabel(item.getCourseName());
                selectOptions.add(option);
            });
        return R.ok("查询成功",selectOptions);
    }



    //构造查询条件
    private Wrapper<Course> buildQueryWrapper(CourseParmBo bo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(StringUtils.isNotEmpty(bo.getCourseName()), Course::getCourseName,bo.getCourseName())
            .eq(StringUtils.isNotEmpty(bo.getCourseType()), Course::getCourseType,bo.getCourseType());
        return wrapper;
    }

    @GetMapping("/getSelectCourse")
    public R<IPage<Course>> getSelectCourse(CourseParmBo courseParm){
        //查询
        IPage<Course> list = courseService.getSelectCourse(courseParm);
        return R.ok("查询成功",list);
    }

    //学生选课
    @PostMapping("/selectCourse")
    public ResultVo selectCourse(@RequestBody StudentCourse studentCourse){
        //查询是否选课
        QueryWrapper<StudentCourse> query = new QueryWrapper<>();
        query.lambda().eq(StudentCourse::getCourseId,studentCourse.getCourseId())
                .eq(StudentCourse::getStuId,studentCourse.getStuId())
        .eq(StudentCourse::getTeacherId,studentCourse.getTeacherId());
        StudentCourse one = studentCourseService.getOne(query);
        if(one != null){
            return ResultUtils.error("您已经选择课程，不用重复选择!");
        }
        boolean save = studentCourseService.save(studentCourse);
        if(save){
            return ResultUtils.success("选择成功!");
        }
        return ResultUtils.error("选择失败!");
    }

    //学生退课
    @PostMapping("/returnCourse")
    public R<Void> returnCourse(@RequestBody StudentCourse studentCourse){
        //条件
        QueryWrapper<StudentCourse> query = new QueryWrapper<>();
        query.lambda().eq(StudentCourse::getCourseId,studentCourse.getCourseId())
                .eq(StudentCourse::getStuId,studentCourse.getStuId())
        .eq(StudentCourse::getTeacherId,studentCourse.getTeacherId());
        boolean remove = studentCourseService.remove(query);
        if(remove){
            return R.ok("退课成功!");
        }
        return R.fail("退课失败!");
    }

    //学生已选课程列表
    @GetMapping("/getStuCourse")
    public R<IPage<SelectCourseBo>> getStuCourse(CourseParmBo courseParm){
        //查询
        IPage<SelectCourseBo> list = courseService.getStuCourse(courseParm);
        return R.ok("查询成功",list);
    }

    //查询教师课程列表
    @GetMapping("/getTeacherCourse")
    public R<IPage<Course>> getTeacherCourse(CourseParmBo courseParm){
        IPage<Course> list = courseService.getTeacherCourse(courseParm);
        return R.ok("查询成功",list);
    }
}
