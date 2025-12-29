package com.yakx.web.domain.bo.report;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 26166
 */
@Data
public class ReportParamBo implements Serializable {

    private Long currentPage;

    private Long pageSize;

    /**
     * 查询类型
     * 1 全部预约
     * 2 课程预约
     * 3 维护预约
     */
    private Integer type;

    private List<Integer> roomIds;

    private List<Integer> teacherIds;

    private List<Integer> courseIds;

    private String dateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
