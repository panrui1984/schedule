package com.yakx.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;
import com.yakx.web.domain.schedule.ClassroomMaintenance;

import java.util.List;

public interface ClassroomMaintenanceService extends IService<ClassroomMaintenance> {

    List<ClassroomMaintenance> selectList(MaintenanceBo bo);

}
