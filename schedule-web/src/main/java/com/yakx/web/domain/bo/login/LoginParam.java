package com.yakx.web.domain.bo.login;

import com.yakx.common.core.validate.AddGroup;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空")
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 用户类型
     */
    private String userType;
}
