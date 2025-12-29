package com.yakx.web.domain.vo;

import lombok.Data;

@Data
public class ReserveVo {

    private Long id;
    //教室id
    private Long roomId;

    private Integer isEzviz;

    private String password;

    private Long starTime;

    private Long endTime;

}
