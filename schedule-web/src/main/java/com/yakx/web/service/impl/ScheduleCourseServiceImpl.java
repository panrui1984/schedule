package com.yakx.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.ClassInterval;
import com.yakx.web.domain.ClassRoom;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.bo.ScheduleListParamBo;
import com.yakx.web.domain.bo.schedule.ScheduleListParm;
import com.yakx.web.domain.bo.schedule.ScheduleParam;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import com.yakx.web.domain.bo.ScheduleParamBo;
import com.yakx.web.mapper.ClassRoomMapper;
import com.yakx.web.mapper.ScheduleCourseMapper;
import com.yakx.web.service.ClassroomReserveService;
import com.yakx.web.service.ScheduleCourseService;
import com.yakx.web.util.ClassIntervalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;


/**
 * @author yakx
 */
@RequiredArgsConstructor
@Service
public class ScheduleCourseServiceImpl extends ServiceImpl<ScheduleCourseMapper, ScheduleCourse> implements ScheduleCourseService {

    private final ClassRoomMapper classRoomMapper;

    private final ClassroomReserveService classroomReserveService;

    @Override
    public boolean saveSchedule(ScheduleParamBo scheduleParmBo) {
        List<Integer> sequences = scheduleParmBo.getSequences();
        LocalDate startDate = scheduleParmBo.getStartDate();
        LocalDate endDate = scheduleParmBo.getEndDate();
        //从数据库查询教室时间是否冲突
        List<ScheduleCourseListVo> exists = this.baseMapper.getScheduleList(Wrappers.query()
                .eq("cs.room_id", scheduleParmBo.getRoomId())
                .in("cs.date_time", startDate)
                .in("cs.sequence", sequences)
                .orderByAsc("cs.date_time,cs.begin_time")
        );

        if (CollUtil.isNotEmpty(exists)) {  //如果时间冲突
            return false;
        }
        //排课入库处理
        List<ScheduleCourse> list = new ArrayList<>();
        for (Integer seq : sequences) {
          //  ScheduleCourse schedule = new ScheduleCourse();
            ScheduleCourse schedule = ScheduleCourse.builder().build();
            schedule.setType(1);
            schedule.setDateTime(scheduleParmBo.getStartDate());
            schedule.setDuration(scheduleParmBo.getDuration());
            schedule.setTeacherId(scheduleParmBo.getTeacherId());
            schedule.setCourseId(scheduleParmBo.getCourseId());
            schedule.setRoomId(scheduleParmBo.getRoomId());
            schedule.setClassId(scheduleParmBo.getClassId());
            schedule.setClassNumber(scheduleParmBo.getClassNumber());
            ClassInterval classInterval = ClassIntervalUtils.getClassInterval(seq);
            schedule.setBeginTime(classInterval.getStartTime());
            schedule.setEndTime(classInterval.getEndTime());
            schedule.setReason(scheduleParmBo.getReason());
            schedule.setRemark(scheduleParmBo.getRemark());
            schedule.setSequence(seq);
            list.add(schedule);
        }

        //记录约课记录
        ClassRoom classRoom = classRoomMapper.selectById(scheduleParmBo.getRoomId());
        ClassroomReserve classroomReserve = new ClassroomReserve();
        classroomReserve.setDateTime(scheduleParmBo.getStartDate());
        classroomReserve.setDuration(scheduleParmBo.getDuration());
        classroomReserve.setTeacherId(scheduleParmBo.getTeacherId());
        classroomReserve.setCourseId(scheduleParmBo.getCourseId());
        classroomReserve.setRoomId(scheduleParmBo.getRoomId());
        classroomReserve.setIsEzviz(classRoom.getIsEzviz());
        classroomReserve.setClassId(scheduleParmBo.getClassId());
        classroomReserve.setClassNumber(scheduleParmBo.getClassNumber());
        //schedule.setBeginTime(scheduleParmBo.getBeginTime());
        //schedule.setEndTime(scheduleParmBo.getEndTime());
        if( CollUtil.isNotEmpty(scheduleParmBo.getSequences()) ){
            //如果是连续约课
            classroomReserve.setSequenceTotal(scheduleParmBo.getSequences().size());
            Integer begin = scheduleParmBo.getSequences().get(0);
         //   ClassInterval interval1 = getTime(begin);
            ClassInterval interval1 = ClassIntervalUtils.getClassInterval(begin);
            Integer end = scheduleParmBo.getSequences().get(scheduleParmBo.getSequences().size() -1 );
            ClassInterval interval2 =  ClassIntervalUtils.getClassInterval(end);
            //    ClassInterval interval2 = getTime(end);
            classroomReserve.setBeginTime(interval1.getStartTime());
            classroomReserve.setEndTime(interval2.getEndTime());
        }
        classroomReserve.setReason(scheduleParmBo.getReason());
        classroomReserve.setRemark(scheduleParmBo.getRemark());

        classroomReserveService.save(classroomReserve);
        for(ScheduleCourse scheduleCourse : list){
            scheduleCourse.setReserveId(classroomReserve.getId());
        }
        //保存
        return this.saveBatch(list);
    }

    @Override
    public List<ScheduleCourseListVo> selectList(ScheduleParam bo){
        Map<String, Object> params = bo.getParams();
        QueryWrapper<ScheduleCourse> wrapper = Wrappers.query();
        wrapper.in(CollUtil.isNotEmpty(bo.getRoomIds()), "cs.room_id", bo.getRoomIds())
                .in(CollUtil.isNotEmpty(bo.getCourseIds()), "cs.course_id", bo.getCourseIds())
                .in(CollUtil.isNotEmpty(bo.getTeacherIds()), "cs.teacher_id", bo.getTeacherIds())
                .eq(StringUtils.isNotEmpty(bo.getDateTime()), "cs.date_time", bo.getDateTime())
                .eq(ObjectUtil.isNotNull(bo.getReserveId()), "cs.reserve_id", bo.getReserveId());
        if( ObjectUtil.isNotNull(params) ){
            wrapper.between( ObjectUtil.isNotNull(params.get("startDate")) && ObjectUtil.isNotNull(params.get("endDate")), "cs.date_time", params.get("startDate"), params.get("endDate"));
        }

        return this.baseMapper.getScheduleList(wrapper);
    }


    @Override
    public List<ScheduleCourse> selectCourseSchedulingList(ScheduleListParm query) {
        //如果是老师本人，只查询老师自己排课列表
        // LoginUser loginUser = LoginHelper.getLoginUser();
        // if(loginUser.getUserType().equals("2")){
        //     query.setTeacherIdList(String.valueOf(loginUser.getUserId()));
        // }else{
        //如果是管理员，则全部查看
        // }
        //   Wrappers.query().in( CollUtil.isNotEmpty(query.getRoomIdList()),"cs.room_id", query.getRoomIdList());
        return this.baseMapper.selectCourseSchedulingList(query);
    }

    @Override
    public List<ScheduleCourse> selectCourseSchedulingList(ScheduleListParamBo bo) {
        return this.baseMapper.selectCourseSchedulingLists(
                Wrappers.query()
                        .in(CollUtil.isNotEmpty(bo.getRoomIdList()), "cs.room_id", bo.getRoomIdList())
                        .in(CollUtil.isNotEmpty(bo.getTeacherIdList()), "cs.teacher_id", bo.getTeacherIdList())
                        .in(CollUtil.isNotEmpty(bo.getCourseIdList()), "cs.course_id", bo.getCourseIdList())
                        .eq(StringUtils.isNotEmpty(bo.getDate()), "cs.date_time", bo.getDate())
        );
    }

}
