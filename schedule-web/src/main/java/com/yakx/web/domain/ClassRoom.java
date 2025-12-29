package com.yakx.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author yaxk
 */
@Data
@TableName("classroom")
public class ClassRoom {
    @TableId(type = IdType.AUTO)
    private Long roomId;

    /**
     * 教室名称
     */
    private String roomName;

    /**
     * 教室地址
     */
    private String roomAddress;

    /**
     * 实验场所代码
     */
    private String code;

    /**
     * 是否电子锁
     */
    private Integer isEzviz;

    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String deviceSerial;

    private String category;

    private String size;

    private String unit;

    private String unitName;

    @OrderBy
    private Integer orderNum;
}
