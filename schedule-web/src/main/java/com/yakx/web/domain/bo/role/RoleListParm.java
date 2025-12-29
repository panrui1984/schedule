package com.yakx.web.domain.bo.role;

import lombok.Data;

@Data
public class RoleListParm {
    private int pageSize;
    private int currentPage;
    private String roleName;
}
