package com.yakx.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StreamUtils;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.ClassInterval;
import com.yakx.web.domain.ClassRoom;
import com.yakx.web.domain.bo.schedule.ScheduleParam;
import com.yakx.web.domain.schedule.ClassroomMaintenance;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.domain.bo.ClassromListParmBo;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;
import com.yakx.web.domain.vo.ScheduleCourseListVo;
import com.yakx.web.mapper.ClassRoomMapper;
import com.yakx.web.service.ClassRoomService;
import com.yakx.web.service.ClassroomMaintenanceService;
import com.yakx.web.service.ScheduleCourseService;
import com.yakx.web.util.ClassIntervalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassRoomMapper, ClassRoom> implements ClassRoomService {

    private final ClassroomMaintenanceService classroomMaintenanceService;

    private final ScheduleCourseService scheduleCourseService;
    @Override
    public IPage<ClassRoom> getList(ClassromListParmBo listParm) {
        //构造分页查询对象
        IPage<ClassRoom> page = new Page<>();
        page.setCurrent(listParm.getCurrentPage());
        page.setSize(listParm.getPageSize());
        //构造查询条件
        QueryWrapper<ClassRoom> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(listParm.getRoomName())){
            query.lambda().like(ClassRoom::getRoomName,listParm.getRoomName())
                .orderByAsc(ClassRoom::getOrderNum);
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public boolean update(ClassRoom classRoom) {
        if( classRoom.getIsEzviz() == 0 ){
            classRoom.setDeviceSerial("");
        }
        return this.baseMapper.updateById(classRoom) > 0;
    }

    @Override
    public List<ClassRoom> getAll(){
        return this.baseMapper.selectList(new QueryWrapper<ClassRoom>());
    }

    /**
     * 新增实验室维护
     */
    @Override
    public boolean addMaintenance(MaintenanceBo bo){
        List<Integer> sequences = bo.getSequences();
        List<ScheduleCourse> list = new ArrayList<>();

        ClassroomMaintenance entity = ClassroomMaintenance.builder()
            .roomId(bo.getRoomId())
            .dateTime(bo.getStartDate())
            .build();
        if( CollUtil.isNotEmpty(bo.getSequences()) ){
            //如果是连续约课
            Integer begin = bo.getSequences().get(0);
            ClassInterval interval1 = ClassIntervalUtils.getClassInterval(begin);
            Integer end = bo.getSequences().get(bo.getSequences().size() -1 );
            ClassInterval interval2 =  ClassIntervalUtils.getClassInterval(end);
            entity.setBeginTime(interval1.getStartTime());
            entity.setEndTime(interval2.getEndTime());
        }
        classroomMaintenanceService.save(entity);
        for (Integer seq : sequences) {
            ScheduleCourse schedule = ScheduleCourse.builder().build();
            schedule.setDateTime(bo.getStartDate());
            schedule.setRoomId(bo.getRoomId());
            schedule.setType(0);
            ClassInterval classInterval = ClassIntervalUtils.getClassInterval(seq);
            schedule.setBeginTime(classInterval.getStartTime());
            schedule.setEndTime(classInterval.getEndTime());
            schedule.setSequence(seq);
            schedule.setReserveId(entity.getId());
            list.add(schedule);
        }
        return scheduleCourseService.saveBatch(list) ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMaintenance(MaintenanceBo bo) {
        ScheduleParam scheduleParam = ScheduleParam.builder().reserveId(bo.getId()).build();
        List<ScheduleCourseListVo> list = scheduleCourseService.selectList(scheduleParam);
        List<Long> ids = StreamUtils.toList(list, ScheduleCourseListVo::getId);
        scheduleCourseService.removeBatchByIds(ids);
        return classroomMaintenanceService.removeById(bo.getId());
    }

}
