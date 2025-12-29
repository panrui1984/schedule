package com.yakx.web.domain.bo;

import lombok.Data;

@Data
public class ClassromListParmBo {
    private Long currentPage;
    private Long pageSize;
    private String roomName;
}
