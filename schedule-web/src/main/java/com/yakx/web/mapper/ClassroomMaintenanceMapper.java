package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yakx.web.domain.schedule.ClassroomMaintenance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomMaintenanceMapper extends BaseMapper<ClassroomMaintenance> {

    List<ClassroomMaintenance> getList(@Param(Constants.WRAPPER) Wrapper<ClassroomMaintenance> queryWrapper);

}
