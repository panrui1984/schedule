package com.yakx.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.bo.report.ReportParamBo;
import com.yakx.web.mapper.ClassroomReserveMapper;
import com.yakx.web.service.ClassroomReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@Service
public class ClassroomReserveServiceImpl extends ServiceImpl<ClassroomReserveMapper, ClassroomReserve> implements ClassroomReserveService {

    @Override
    public IPage<ClassroomReserve> selectPageList(ReportParamBo bo) {
        //构造分页查询对象
        IPage<ClassroomReserve> page = new Page<>();
        page.setCurrent(bo.getCurrentPage());
        page.setSize(bo.getPageSize());
        Map<String, Object> params = bo.getParams();
        //构造查询条件
        QueryWrapper<ClassroomReserve> query = Wrappers.query();
        query.in(CollUtil.isNotEmpty(bo.getRoomIds()), "cs.room_id", bo.getRoomIds())
                .in(CollUtil.isNotEmpty(bo.getTeacherIds()), "cs.teacher_id", bo.getTeacherIds())
                .in(CollUtil.isNotEmpty(bo.getCourseIds()), "cs.course_id", bo.getCourseIds())
                .between(params.get("startDate") != null && params.get("endDate") != null,
                        "cs.date_time", params.get("startDate"), params.get("endDate"));
        return this.baseMapper.getPageList(page,query);
    }


    @Override
    public List<ClassroomReserve> selectList(ReportParamBo bo) {
        Map<String, Object> params = bo.getParams();
        //构造查询条件
        QueryWrapper<ClassroomReserve> query = Wrappers.query();
        query.in(CollUtil.isNotEmpty(bo.getRoomIds()), "cs.room_id", bo.getRoomIds())
                .in(CollUtil.isNotEmpty(bo.getTeacherIds()), "cs.teacher_id", bo.getTeacherIds())
                .in(CollUtil.isNotEmpty(bo.getCourseIds()), "cs.course_id", bo.getCourseIds());

        // 日期时间精确匹配条件
        if (StringUtils.isNotEmpty(bo.getDateTime())) {
            query.eq("cs.date_time", bo.getDateTime());
        }


        // 日期时间范围条件
        Date startDate = (Date) params.get("startDate");
        Date endDate = (Date) params.get("endDate");
        if (startDate != null && endDate != null) {
            // 如果提供了开始和结束日期，则使用这些日期
            query.between("cs.date_time", startDate, endDate);
        } else {
            // 否则，默认查询前后一个月的数据
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneMonthAgo = now.minusMonths(3);
            LocalDateTime oneMonthLater = now.plusMonths(3);

            // 转换为 Date 类型
            Date defaultStartDate = Date.from(oneMonthAgo.atZone(ZoneId.systemDefault()).toInstant());
            Date defaultEndDate = Date.from(oneMonthLater.atZone(ZoneId.systemDefault()).toInstant());

            query.between("cs.date_time", defaultStartDate, defaultEndDate);
        }

               // .eq(StringUtils.isNotEmpty(bo.getDateTime()), "cs.date_time", bo.getDateTime())                .between(params.get("startDate") != null && params.get("endDate") != null,
                //        "cs.date_time", params.get("startDate"), params.get("endDate"));
        /*
        query.in(CollUtil.isNotEmpty(bo.getRoomIds()), "cs.room_id", bo.getRoomIds())
            .in(CollUtil.isNotEmpty(bo.getTeacherIds()), "cs.teacher_id", bo.getTeacherIds())
            .in(CollUtil.isNotEmpty(bo.getCourseIds()), "cs.course_id", bo.getCourseIds())
            .eq(StringUtils.isNotEmpty(bo.getDateTime()), "cs.date_time", bo.getDateTime())                .between(params.get("startDate") != null && params.get("endDate") != null,
                "cs.date_time", params.get("startDate"), params.get("endDate"));
        */

        return this.baseMapper.getList(query);
    }

    /**
     * 获取指定日期的最后到期的预约
     * 用于密码锁每天额度超额时拿到最长到期密码
     * @param dateTime
     * @param roomId
     * @return
     */
    @Override
    public String getLastClassroomReservePwd(String dateTime, Long roomId){
        ReportParamBo bo = new ReportParamBo();
        bo.setDateTime(dateTime);
        QueryWrapper<ClassroomReserve> query = Wrappers.query();
        query.eq(ObjectUtil.isNotNull(roomId), "cs.room_id", roomId)
            .eq(StringUtils.isNotEmpty(dateTime), "cs.date_time", dateTime)
            .orderByDesc("cs.end_time");
        ClassroomReserve theLast = this.baseMapper.selectOne(query);
        return theLast.getPassword();
    }

}
