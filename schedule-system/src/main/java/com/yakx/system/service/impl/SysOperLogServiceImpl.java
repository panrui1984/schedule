package com.yakx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.core.domain.event.OperLogEvent;
import com.yakx.common.utils.MapstructUtils;
import com.yakx.system.domain.SysLogininfor;
import com.yakx.system.domain.SysOperLog;
import com.yakx.system.domain.bo.SysOperLogBo;
import com.yakx.system.mapper.SysLogininforMapper;
import com.yakx.system.mapper.SysOperLogMapper;
import com.yakx.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>  implements ISysOperLogService {

    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    @Async
    @EventListener
    public void recordOper(OperLogEvent operLogEvent) {
        SysOperLog operLog = BeanUtil.toBean(operLogEvent, SysOperLog.class);
        insertOperlog(operLog);
    }

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLog.setOperTime(new Date());
        baseMapper.insert(operLog);
    }

    @Override
    public IPage<SysOperLog> selectOperLogList(SysOperLogBo bo) {
        //构造分页查询对象
        IPage<SysOperLog> page = new Page<>();
        page.setCurrent(bo.getCurrentPage());
        page.setSize(bo.getPageSize());
        //构造查询条件
        QueryWrapper<SysOperLog> query = new QueryWrapper<>();

        return this.baseMapper.selectPage(page,query);
    }
}
