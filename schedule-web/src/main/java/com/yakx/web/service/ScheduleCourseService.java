package com.yakx.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.bo.ScheduleListParamBo;
import com.yakx.web.domain.bo.schedule.ScheduleParam;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import com.yakx.web.domain.bo.schedule.ScheduleListParm;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.domain.bo.ScheduleParamBo;
import java.util.List;

public interface ScheduleCourseService extends IService<ScheduleCourse> {

    //预约实验室
    boolean saveSchedule(ScheduleParamBo scheduleParmBo);

    List<ScheduleCourseListVo> selectList(ScheduleParam bo);


    //查询排课列表
    List<ScheduleCourse> selectCourseSchedulingList(ScheduleListParm listParm);

    List<ScheduleCourse> selectCourseSchedulingList(ScheduleListParamBo bo);

}
