package com.yakx.common.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    private Long userId;
    //排除该字段
    private Long roleId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private String isAdmin;
    //帐户是否过期(1 未过期，0已过期)
    private boolean isAccountNonExpired = true;
    //帐户是否被锁定(1 未锁定，0已锁定)
    private boolean isAccountNonLocked = true;
    //密码是否过期(1 未过期，0已过期)
    private boolean isCredentialsNonExpired = true;
    //帐户是否可用(1 可用，0 删除用户)
    private boolean isEnabled = true;
    private String name;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 获取登录id
     */
    public String getLoginId() {
        /*
        if (userType == null) {
            throw new IllegalArgumentException("用户类型不能为空");
        }
        */
        if(userType == null){
            userType = "sys_user";
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return userType + ":" + userId;
    }
}
