package com.yakx.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.ClassroomEzvizPwd;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ClassroomEzvizPwdService extends IService<ClassroomEzvizPwd> {

    String getClassroomEzvizPwd(Long roomId, LocalDate dateTime, LocalTime beginTime, LocalTime endTime);

    String addClassroomEzvizPwd(Long roomId, String userName, String deviceSerial, LocalDate dateTime, LocalTime beginTime, LocalTime endTime) throws Exception;

    /**
     * 定时任务，设置每天执行设置教室门锁密码
     */
    void batchAddEzvizPwd();
}
