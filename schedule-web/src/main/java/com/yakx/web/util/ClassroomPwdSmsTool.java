package com.yakx.web.util;

import com.yakx.common.utils.VelocityInitializer;
import com.yakx.common.utils.VelocityUtils;
import com.yakx.web.domain.vo.ClassroomReservationSmsMessage;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ClassroomPwdSmsTool {
    public static String buildMessage(ClassroomReservationSmsMessage smsMessage){
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("teacherName", smsMessage.getTeacherName());
        dataModel.put("roomName", smsMessage.getRoomName());
        dataModel.put("password", smsMessage.getPassword());
        dataModel.put("dateTime", smsMessage.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dataModel.put("startTime", smsMessage.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        dataModel.put("endTime", smsMessage.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(dataModel);
        Template tpl = Velocity.getTemplate("vm/classroompwd.vm");
        StringWriter sw = new StringWriter();
        tpl.merge(context, sw);
        return sw.toString();
    }
}
