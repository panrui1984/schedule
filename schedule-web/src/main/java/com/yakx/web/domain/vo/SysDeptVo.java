package com.yakx.web.domain.vo;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

@Data
public class SysDeptVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人ID
     */
    private Long leader;

    /**
     * 负责人
     */
    private String leaderName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

}

