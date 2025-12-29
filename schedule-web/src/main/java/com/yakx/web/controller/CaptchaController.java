package com.yakx.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.AbstractCaptcha;
import com.yakx.common.constant.GlobalConstants;
import com.yakx.common.core.domain.R;
import com.yakx.common.enums.CaptchaType;
import com.yakx.common.utils.CacheUtil;
import com.yakx.common.utils.StringUtils;
import com.yakx.common.utils.reflect.ReflectUtils;
import com.yakx.common.utils.spring.SpringUtils;
import com.yakx.framework.config.properties.CaptchaProperties;
import com.yakx.web.domain.vo.CaptchaVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.util.IdUtil;
import cn.hutool.captcha.generator.CodeGenerator;
@SaIgnore
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class CaptchaController {

    private final CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @GetMapping("/code")
    public R<CaptchaVo> getCode() {
        CaptchaVo captchaVo = new CaptchaVo();
        boolean captchaEnabled = captchaProperties.getEnable();
        if (!captchaEnabled) {
            captchaVo.setCaptchaEnabled(false);
            return R.ok(captchaVo);
        }
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;
        // 生成验证码
        CaptchaType captchaType = captchaProperties.getType();
        boolean isMath = CaptchaType.MATH == captchaType;
        Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
        CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
        AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());
        captcha.setGenerator(codeGenerator);
        captcha.createCode();
        String code = captcha.getCode();
        if (isMath) {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
            code = exp.getValue(String.class);
        }
        CacheUtil.put("captcha", verifyKey, code);
        captchaVo.setUuid(uuid);
        captchaVo.setImg(captcha.getImageBase64());
        return R.ok(captchaVo);
    }

}
