package com.yakx.web.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.ClassRoom;
import com.yakx.web.domain.bo.ClassromListParmBo;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;

public interface ClassRoomService extends IService<ClassRoom> {
    IPage<ClassRoom> getList(ClassromListParmBo listParm);

    List<ClassRoom> getAll();

    boolean update(ClassRoom classRoom);

    boolean addMaintenance(MaintenanceBo bo);

    boolean removeMaintenance(MaintenanceBo bo);
}
