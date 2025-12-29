package com.yakx.web.domain.bo.schedule;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ScheduleParam implements Serializable {

    private Long currentPage;

    private Long pageSize;

    private List<Integer> roomIds;

    private List<Integer> teacherIds;

    private List<Integer> courseIds;

    private String dateTime;

    private Long reserveId;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
