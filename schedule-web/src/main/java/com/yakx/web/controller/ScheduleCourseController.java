package com.yakx.web.controller;

import com.yakx.common.annotation.Log;
import com.yakx.common.core.domain.R;
import com.yakx.common.core.domain.model.LoginUser;
import com.yakx.common.enums.BusinessType;
import com.yakx.common.helper.LoginHelper;
import com.yakx.web.domain.*;
import com.yakx.web.domain.bo.ScheduleListParamBo;
import com.yakx.web.domain.bo.schedule.ScheduleListParm;
import com.yakx.web.domain.bo.schedule.ScheduleParam;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import com.yakx.web.service.*;
import com.yakx.web.domain.bo.ScheduleParamBo;
import com.yakx.web.domain.vo.SelectOptionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule")
public class ScheduleCourseController {

    private final ScheduleCourseService scheduleCourseService;

    private final ClassroomEzvizPwdService classroomEzvizPwdService;

    @Log(title = "排课管理", businessType = BusinessType.INSERT)
    @PostMapping("/saveSchedule")
    public R<Void> saveSchedule(@RequestBody ScheduleParamBo scheduleParamBo){
        try{
            boolean save = scheduleCourseService.saveSchedule(scheduleParamBo);
            if(save){
                return R.ok("预约成功!");
            }
            return R.fail("预约失败!");
        }catch (Exception ex){
            return R.fail(ex.getMessage());
        }
    }


  /*   @GetMapping("/getScheduleLists")
    public R<List<ScheduleCourse>> getScheduleLists(ScheduleListParamBo bo){
        //参数组装
        List<ScheduleCourse> list = scheduleCourseService.selectCourseSchedulingList(bo);
        return R.ok("查询成功",list);
    }

*/



    //排课列表查询
/*    @GetMapping("/getScheduleList")
    public R<List<ScheduleCourse>> getScheduleList(ScheduleListParm listParm){
        //参数组装
        List<ScheduleCourse> list = scheduleCourseService.selectCourseSchedulingList(listParm);
        return R.ok("查询成功",list);
    }*/

    /**
     * 查询指定日期排课清单
     * @param roomId
     * @param date
     */
    @GetMapping("/getScheduleListBySelectedDate")
    public R<List<ScheduleCourseListVo>> getScheduleListBySelectedDate(Integer roomId, String date){
        ScheduleParam bo = ScheduleParam.builder().roomIds(Arrays.asList(roomId)).dateTime(date).build();
        List<ScheduleCourseListVo> list = scheduleCourseService.selectList(bo);
        return R.ok("查询成功",list);
    }


    //编辑排课
    @Log(title = "排课管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateScheduleById")
    public R<Void> updateScheduleById(@Validated @RequestBody ScheduleCourse scheduleCourse){
        boolean save = scheduleCourseService.updateById(scheduleCourse);
        if(save){
            return R.ok("编辑成功!");
        }
        return R.fail("编辑失败!");
    }

    //删除排课
    @Log(title = "排课管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeScheduleById")
    public R<Void> removeScheduleById(@Validated @RequestBody ScheduleCourse scheduleCourse){
        boolean delete = scheduleCourseService.removeById(scheduleCourse);
        if(delete){
            return R.ok("删除成功!");
        }
        return R.fail("删除失败!");
    }
}
