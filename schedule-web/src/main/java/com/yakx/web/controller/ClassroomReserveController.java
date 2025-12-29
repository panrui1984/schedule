package com.yakx.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.yakx.common.annotation.Log;
import com.yakx.common.core.domain.R;
import com.yakx.common.enums.BusinessType;
import com.yakx.common.utils.StreamUtils;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;
import com.yakx.web.domain.schedule.ClassroomMaintenance;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.bo.report.ReportParamBo;
import com.yakx.web.domain.bo.schedule.ScheduleParam;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import com.yakx.web.domain.vo.schedule.Reservation;
import com.yakx.web.service.ClassroomMaintenanceService;
import com.yakx.web.service.ClassroomReserveService;
import com.yakx.web.service.ScheduleCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/classroomReserve")
public class ClassroomReserveController {

    private final ClassroomReserveService classroomReserveService;

    private final ScheduleCourseService scheduleCourseService;

    private final ClassroomMaintenanceService classroomMaintenanceService;

    /**
     * 获取实验室预约结果列表
     *
     */
    @GetMapping("/list")
    public R<List<Reservation>> getList(ReportParamBo bo) {
        //查询实验室实训列表
        List<ClassroomReserve> list = classroomReserveService.selectList(bo);

        List<Reservation> result = new ArrayList<>();
        for (ClassroomReserve cr : list) {
            Reservation reservation = Reservation.builder()
                .id(cr.getId())
                .className(cr.getClassName())
                .classNumber(cr.getClassNumber())
                .courseName(cr.getCourseName())
                .courseColor(cr.getCourseColor())
                .endTime(cr.getEndTime())
                .beginTime(cr.getBeginTime())
                .dateTime(cr.getDateTime())
                .teacherName(cr.getTeacherName())
                .sequenceTotal(cr.getSequenceTotal())
                .roomName(cr.getRoomName())
                .roomId(cr.getRoomId())
                .password(cr.getPassword())
                .type(1).
                build();
            result.add(reservation);
        }
        if(bo.getType() == 2)   //如果只查询约课记录，则直接返回
            return  R.ok("查询成功", result);
        //获取实训维护清单
        MaintenanceBo cm = MaintenanceBo.builder().build();
        if(StringUtils.isNotEmpty(bo.getDateTime())){
            cm.setDateTime(LocalDate.parse(bo.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if(CollUtil.isNotEmpty(bo.getRoomIds())){
            cm.setRoomIds(bo.getRoomIds());
        }
        List<ClassroomMaintenance> maintenanceList = classroomMaintenanceService.selectList(cm);


        for (ClassroomMaintenance entity : maintenanceList) {
            Reservation reservation =Reservation.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .roomName(entity.getRoomName())
                .dateTime(entity.getDateTime())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .type(0)
                .build();
            result.add(reservation);
        }
        return R.ok("查询成功", result);
    }

    @Log(title = "约课管理", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable("id") Long id){
        boolean delete = classroomReserveService.removeById(id);
        //同步检索并删除排课表记录
        ScheduleParam bo = ScheduleParam.builder().reserveId(id).build();
        List<ScheduleCourseListVo> scheduleCourseList = scheduleCourseService.selectList(bo);
        //删除清单
        List<Long> list = StreamUtils.toList(scheduleCourseList, ScheduleCourseListVo::getId);
        boolean remove = scheduleCourseService.removeBatchByIds(list);
        if (delete && remove) {
            return R.ok("删除成功!");
        }
        return R.fail("删除失败!");
    }

}
