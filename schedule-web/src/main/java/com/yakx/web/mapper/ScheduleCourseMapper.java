package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yakx.web.domain.bo.schedule.ScheduleListParm;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleCourseMapper extends BaseMapper<ScheduleCourse> {
    List<ScheduleCourseListVo> getScheduleList(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    IPage<ScheduleCourseListVo> getList(IPage<ScheduleCourse> page,@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    //排列列表
    List<ScheduleCourse> selectCourseSchedulingList(@Param("parm") ScheduleListParm parm);

    List<ScheduleCourse> selectCourseSchedulingLists(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    //获取个人排课记录
    List<ScheduleCourse> selectOwnCourseSchedulingList(@Param("teacherId") String teacherId);

}
