package com.yakx.web.domain;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yakx.common.convert.ExcelLocalTimeConvert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("classroom_ezviz_pwd")
public class ClassroomEzvizPwd {

    @TableId(type = IdType.AUTO)
    private Long id;

    //教室id
    private Long roomId;

    //设备序列号
    private String deviceSerial;

    //电子锁密码
    private String password;

    //日期
    private LocalDate dateTime;

    //开始时间
    private LocalTime beginTime;

    //结束时间
    private LocalTime endTime;

}
