package com.yakx.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.velocity.VelocityContext;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VelocityUtils {

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(Map<String, Object> dataModel) {
        VelocityContext velocityContext = new VelocityContext();
        dataModel.forEach(velocityContext::put);
        return velocityContext;
    }
}
