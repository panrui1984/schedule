package com.yakx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.system.domain.SysOperLog;
import com.yakx.system.domain.bo.SysOperLogBo;

import java.util.List;

public interface ISysOperLogService {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    IPage<SysOperLog> selectOperLogList(SysOperLogBo bo);

}
