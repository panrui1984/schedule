package com.yakx.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.bo.report.ReportParamBo;
import java.util.List;

public interface ClassroomReserveService extends IService<ClassroomReserve> {

    /**
     * 分页报表清单查询
     */
    IPage<ClassroomReserve> selectPageList(ReportParamBo bo);

    /*
     * 报表清单
     */

    List<ClassroomReserve> selectList(ReportParamBo bo);

    /**
     * 获取指定日期的最后到期的预约
     * @param dateTime
     * @param roomId
     * @return
     */
    String getLastClassroomReservePwd(String dateTime, Long roomId);

}
