package com.yakx.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.PageQuery;
import com.yakx.common.core.domain.R;
import com.yakx.common.core.page.TableDataInfo;
import com.yakx.system.domain.SysOperLog;
import com.yakx.system.domain.bo.SysOperLogBo;
import com.yakx.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/operlog")
public class SysOperlogController {

    private final ISysOperLogService operLogService;

    /**
     * 获取操作日志记录列表
     */

}
