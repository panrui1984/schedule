package com.yakx.web.domain.bo;

import lombok.Data;

@Data
public class CourseParmBo {
    private Long currentPage;
    private Long pageSize;
    private Long stuId;
    private Long teacherId;
    private String courseName;
    private String courseType;
}