package com.yakx.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.R;
import com.yakx.system.domain.EzvizLog;
import com.yakx.system.domain.bo.EzvizLogBo;
import com.yakx.system.service.IEzvizLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/ezvizLog")
public class EzvizLogController {

    private final IEzvizLogService ezvizLogService;

    //分页查询
    @GetMapping("/list")
    public R<IPage<EzvizLog>> getList(EzvizLogBo bo) {
        IPage<EzvizLog> list = ezvizLogService.getList(bo);
        return R.ok("查询成功", list);
    }

}
