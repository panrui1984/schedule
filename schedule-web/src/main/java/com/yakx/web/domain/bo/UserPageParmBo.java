package com.yakx.web.domain.bo;

import lombok.Data;

@Data
public class UserPageParmBo {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String name;
}