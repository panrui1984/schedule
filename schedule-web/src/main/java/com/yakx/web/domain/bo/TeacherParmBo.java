package com.yakx.web.domain.bo;

import lombok.Data;

@Data
public class TeacherParmBo {
    private Long currentPage;
    private Long pageSize;
    private String teacherName;
}
