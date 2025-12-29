package com.yakx.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;
import com.yakx.web.domain.schedule.ClassroomMaintenance;
import com.yakx.web.domain.schedule.ScheduleCourse;
import com.yakx.web.mapper.ClassroomMaintenanceMapper;
import com.yakx.web.service.ClassroomMaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassroomMaintenanceServiceImpl extends ServiceImpl<ClassroomMaintenanceMapper, ClassroomMaintenance> implements ClassroomMaintenanceService {
    @Override
    public List<ClassroomMaintenance> selectList(MaintenanceBo bo) {
        QueryWrapper<ClassroomMaintenance> wrapper = Wrappers.query();
        wrapper.eq(ObjectUtils.isNotNull(bo.getDateTime()), "cm.date_time", bo.getDateTime());
        wrapper.in(CollUtil.isNotEmpty(bo.getRoomIds()), "cm.room_id", bo.getRoomIds());
        return this.baseMapper.getList(wrapper);
    }
}
