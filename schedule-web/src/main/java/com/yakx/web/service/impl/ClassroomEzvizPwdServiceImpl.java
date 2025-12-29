package com.yakx.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StreamUtils;
import com.yakx.common.utils.StringUtils;
import com.yakx.common.utils.email.MailUtils;
import com.yakx.ezviz.EzvizClient;
import com.yakx.ezviz.exception.EzvizException;
import com.yakx.web.domain.ClassRoom;
import com.yakx.web.domain.ClassroomEzvizPwd;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.vo.ClassroomReservationSmsMessage;
import com.yakx.web.mapper.ClassroomEzvizPwdMapper;
import com.yakx.web.service.ClassRoomService;
import com.yakx.web.service.ClassroomEzvizPwdService;
import com.yakx.web.service.ClassroomReserveService;
import com.yakx.web.service.TeacherService;
import com.yakx.web.util.ClassroomPwdSmsTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.enumerate.SupplierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yakx
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ClassroomEzvizPwdServiceImpl extends ServiceImpl<ClassroomEzvizPwdMapper, ClassroomEzvizPwd> implements ClassroomEzvizPwdService {

    private static final Logger logger = LoggerFactory.getLogger(ClassroomEzvizPwdServiceImpl.class);

    private final ClassroomReserveService classroomReserveService;

    private final ClassRoomService classroomService;

    private final TeacherService teacherService;

    @Override
    public String getClassroomEzvizPwd(Long roomId, LocalDate dateTime, LocalTime beginTime, LocalTime endTime) {
        QueryWrapper<ClassroomEzvizPwd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassroomEzvizPwd::getRoomId, roomId).eq(ClassroomEzvizPwd::getDateTime, dateTime).le(ClassroomEzvizPwd::getBeginTime, beginTime).ge(ClassroomEzvizPwd::getEndTime, endTime);
        List<ClassroomEzvizPwd> list = this.baseMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0).getPassword();
        }
        return null;
    }

    /**
     * 每天定时清理临时密码
     */
    @Scheduled(cron = "0 30 6 * * ?")
    public void batchRemoveEzvizPwd(){
        logger.info(" 开始执行定时任务，设置 {} 电子门锁密码", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LambdaQueryWrapper<ClassRoom> wrapper = new LambdaQueryWrapper<ClassRoom>().eq(ClassRoom::getIsEzviz, 1);
        List<ClassRoom> roomList = classroomService.list(wrapper);
        if (CollUtil.isEmpty(roomList)) {
            return;
        }
        List<String> ids = StreamUtils.toList(roomList, ClassRoom::getDeviceSerial);
        EzvizClient client = EzvizClient.getEzvizClient();
        client.batchRemoveTemporaryKey(ids);
    }


    @Override
    @Scheduled(cron = "0 0 7 * * ?")
    public void batchAddEzvizPwd() {
        LocalDate now = LocalDate.now();
        logger.info(" 开始执行定时任务，设置 {} 电子门锁密码", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //查询当天配置电子门锁约课实验室清单
        LambdaQueryWrapper<ClassroomReserve> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ClassroomReserve::getIsEzviz, 1).eq(ClassroomReserve::getDateTime, now);
        List<ClassroomReserve> list = classroomReserveService.list(wrapper);
        if (CollUtil.isEmpty(list)) {
            return;
        }
        logger.info(" 需要申请密码任务共 {} 个", list.size());
        Map<Long, List<ClassroomReserve>> classRoomDataMap = StreamUtils.groupByKey(list, ClassroomReserve::getRoomId);
        classRoomDataMap.forEach((k, v) -> {
            List<ClassroomReserve> dictList = StreamUtils.sorted(v, Comparator.comparing(ClassroomReserve::getBeginTime));
            //如果当天某个教室约课次数超过3次，则第三次的结束直接直接设置为当天结束时间
            int i = 0;
            String pwd = null;
            ClassRoom classRoom = classroomService.getById(k);


            for (ClassroomReserve reserve : dictList) {
                i++;
                Long roomId = reserve.getRoomId();
                LocalDate day = reserve.getDateTime();
                //预约的实际时间
                LocalTime begin = reserve.getBeginTime();
                LocalTime end = reserve.getEndTime();
                //默认临时密码时间前后延长60分钟
                LocalTime extendBeginTime = begin.plusMinutes(-60);
                LocalTime extendEndTime = end.plusMinutes(60);
                Teacher teacher = teacherService.getById(reserve.getTeacherId());
                try {
                    if (i < 3) {    //前两次
                        ClassroomEzvizPwd entity = new ClassroomEzvizPwd();
                        try {
                            pwd = addClassroomEzvizPwd(roomId, teacher.getTeacherName(), classRoom.getDeviceSerial(), day, extendBeginTime, extendEndTime);

                        }catch (EzvizException ex ){
                            //门锁密码获取失败时发送通知消息
                            MailUtils.sendText(teacher.getEmail(), "电子门锁密码", classRoom.getRoomName() + " 密码获取失败, " + ex.getMessage() + " , 请联系实验室管理员.");
                            continue;
                        }
                        entity.setPassword(pwd);
                        ClassroomReserve update = new ClassroomReserve();   //保存密码
                        update.setId(reserve.getId());
                        update.setPassword(pwd);
                        classroomReserveService.updateById(update);

                        entity.setDeviceSerial(classRoom.getDeviceSerial());
                        entity.setRoomId(roomId);
                        entity.setDateTime(day);
                        entity.setBeginTime(extendBeginTime);
                        entity.setEndTime(extendEndTime);
                        this.baseMapper.insert(entity);
                    } else if (i == 3) {
                        //第三次临时密码延长到22点
                        end = LocalTime.of(22, 0);


                        pwd = addClassroomEzvizPwd(roomId, teacher.getTeacherName(), classRoom.getDeviceSerial(), day, extendBeginTime, end);

                        ClassroomReserve update = new ClassroomReserve();   //保存密码
                        update.setId(reserve.getId());
                        update.setPassword(pwd);
                        classroomReserveService.updateById(update);
                        ClassroomEzvizPwd entity = ClassroomEzvizPwd.builder()
                            .password(pwd)
                            .deviceSerial(classRoom.getDeviceSerial())
                            .roomId(roomId)
                            .dateTime(day)
                            .beginTime(extendBeginTime)
                            .endTime(extendEndTime)
                            .build();
                        this.baseMapper.insert(entity);
                    } else {
                        //如果预约3次以上，则取最后一次获取的密码
                        ClassroomEzvizPwd entity = new ClassroomEzvizPwd();
                        //如果密码为空
                        entity.setPassword(pwd);    //pwd是上一次密码
                        ClassroomReserve update = new ClassroomReserve();   //保存密码
                        update.setId(reserve.getId());
                        update.setPassword(pwd);
                        classroomReserveService.updateById(update);
                        entity.setDeviceSerial(classRoom.getDeviceSerial());
                        entity.setRoomId(roomId);
                        entity.setDateTime(day);
                        entity.setBeginTime(extendBeginTime);
                        entity.setEndTime(extendEndTime);
                        this.baseMapper.insert(entity);
                    }

                    if( StringUtils.isNotEmpty(teacher.getEmail())){
                        //发送邮件
                        ClassroomReservationSmsMessage message = ClassroomReservationSmsMessage
                            .builder()
                            .teacherName(teacher.getTeacherName())
                            .roomName(classRoom.getRoomName())
                            .password(pwd)
                            .dateTime(day)
                            .startTime(extendBeginTime)
                            .endTime(extendEndTime)
                            .build();
                        String msgContent = ClassroomPwdSmsTool.buildMessage(message);
                        MailUtils.sendText(teacher.getEmail(), "电子门锁密码", msgContent);

                    }
                    if (StringUtils.isNotEmpty(teacher.getPhone())) {
                        //发送短消息
                        LinkedHashMap<String, String> smsMap = new LinkedHashMap<>(1);
                        smsMap.put("roomName", classRoom.getRoomAddress());
                        smsMap.put("password", pwd);
                        smsMap.put("dateTime", day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        smsMap.put("startTime", extendBeginTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                        smsMap.put("endTime", extendEndTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                        SmsBlend smsBlend = SmsFactory.createSmsBlend(SupplierType.ALIBABA);
                        SmsResponse smsResponse = smsBlend.sendMessage(teacher.getPhone(), "SMS_464321228", smsMap);
                        if (!"OK".equals(smsResponse.getCode())) {
                            log.error("验证码短信发送异常 => {}", smsResponse);
                            logger.error(smsResponse.getMessage());
                        }
                    }
                } catch (Exception ex) {
                    //门锁密码获取失败时发送通知消息
                    MailUtils.sendText(teacher.getEmail(), "电子门锁密码", "密码获取失败, " + ex.getMessage() + " , 请联系实验室管理员.");
                }


            }
        });
    }


    @Override
    public String addClassroomEzvizPwd(Long roomId, String userName, String deviceSerial, LocalDate dateTime, LocalTime extendBeginTime, LocalTime extendEndTime) throws EzvizException {
        LocalDateTime begin = LocalDateTime.of(dateTime, extendBeginTime);
        LocalDateTime end = LocalDateTime.of(dateTime, extendEndTime);
        EzvizClient client = EzvizClient.getEzvizClient();
        JSONObject result = client.addTemporaryKey(
            deviceSerial,
            userName,
            begin.toEpochSecond(ZoneOffset.ofHours(8)),
            end.toEpochSecond(ZoneOffset.ofHours(8))
        );
        return result.getString("pwd");
    }
}
