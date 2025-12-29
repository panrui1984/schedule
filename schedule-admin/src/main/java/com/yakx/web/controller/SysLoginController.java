package com.yakx.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.yakx.common.constant.Constants;
import com.yakx.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证
 */

@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
       // String token = loginService.xcxLogin(xcxCode);
       // ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }


}
