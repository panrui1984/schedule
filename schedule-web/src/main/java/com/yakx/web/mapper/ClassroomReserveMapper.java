package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yakx.web.domain.schedule.ClassroomReserve;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomReserveMapper extends BaseMapper<ClassroomReserve> {

    List<ClassroomReserve> getList(@Param(Constants.WRAPPER) Wrapper<ClassroomReserve> queryWrapper);

    IPage<ClassroomReserve> getPageList(IPage<ClassroomReserve> page, @Param(Constants.WRAPPER) Wrapper<ClassroomReserve> queryWrapper);

}
