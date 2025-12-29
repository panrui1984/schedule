package com.yakx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.core.domain.event.EzvizLogEvent;
import com.yakx.common.utils.StringUtils;
import com.yakx.system.domain.EzvizLog;
import com.yakx.system.domain.SysOperLog;
import com.yakx.system.domain.bo.EzvizLogBo;
import com.yakx.system.domain.bo.SysOperLogBo;
import com.yakx.system.mapper.EzvizLogMapper;
import com.yakx.system.service.IEzvizLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Date;


@RequiredArgsConstructor
@Service
public class IEzvizLogServiceImpl extends ServiceImpl<EzvizLogMapper, EzvizLog> implements IEzvizLogService {

    @Async
    @EventListener
    public void recordEzvizLog(EzvizLogEvent event) {
        EzvizLog log = new EzvizLog();
        log.setCode(event.getCode());
        log.setMessage(event.getMessage());
        log.setDeviceSerial(event.getDeviceSerial());
        log.setModule(event.getModule());
        log.setBusinessType(event.getBusinessType());
        log.setMethod(event.getMethod());
        // 插入数据
        insertEzvizLog(log);
    }


    @Override
    public void insertEzvizLog(EzvizLog log) {
        log.setOperTime(new Date());
        this.baseMapper.insert(log);
    }

    @Override
    public IPage<EzvizLog> getList(EzvizLogBo bo){
        IPage<EzvizLog> page = new Page<>();
        page.setCurrent(bo.getCurrentPage());
        page.setSize(bo.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<EzvizLog> query = new LambdaQueryWrapper<EzvizLog>()
            .eq(StringUtils.isNotEmpty(bo.getMethod()), EzvizLog::getMethod, bo.getMethod())
            .orderByDesc(EzvizLog::getOperTime);
        return this.baseMapper.selectPage(page,query);
    }
}
