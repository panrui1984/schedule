package com.yakx.web.domain.bo;


import lombok.Data;

import java.util.List;

@Data
public class SaveAssignBo {
    private Long roleId;
    private List<Long> list;
}