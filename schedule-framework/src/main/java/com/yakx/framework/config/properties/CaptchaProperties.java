package com.yakx.framework.config.properties;

import com.yakx.common.enums.CaptchaCategory;
import com.yakx.common.enums.CaptchaType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码 配置属性
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    private Boolean enable;

    /**
     * 验证码类型
     */
    private CaptchaType type;

    /**
     * 验证码类别
     */
    private CaptchaCategory category;

    /**
     * 数字验证码位数
     */
    private Integer numberLength;

    /**
     * 字符验证码长度
     */
    private Integer charLength;
}
