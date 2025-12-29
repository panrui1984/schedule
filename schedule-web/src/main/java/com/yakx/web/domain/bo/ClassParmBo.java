package com.yakx.web.domain.bo;

import lombok.Data;


@Data
public class ClassParmBo {
    private Long currentPage;
    private Long pageSize;
    private String className;
}
