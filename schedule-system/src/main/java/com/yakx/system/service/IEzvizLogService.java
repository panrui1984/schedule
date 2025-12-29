package com.yakx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.system.domain.EzvizLog;
import com.yakx.system.domain.bo.EzvizLogBo;

public interface IEzvizLogService {

    /**
     * 新增接口日志
     *
     * @param log 访问日志对象
     */
    void insertEzvizLog(EzvizLog log);

    IPage<EzvizLog> getList(EzvizLogBo bo);

}
