package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.R;
import com.yakx.common.utils.poi.ExcelUtil;
import com.yakx.web.domain.schedule.ClassroomReserve;
import com.yakx.web.domain.bo.report.ReportParamBo;
import com.yakx.web.service.ClassroomReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ClassroomReserveService classroomReserveService;

    /**
     * 查询约课记录
     */
    @GetMapping("/list")
    public R<IPage<ClassroomReserve>> getList(ReportParamBo reportParamBo){
        IPage<ClassroomReserve> list = classroomReserveService.selectPageList(reportParamBo);
        return R.ok("查询成功", list);
    }

    /**
     * 导出约课记录
     */
    @PostMapping("/export")
    public void export(ReportParamBo reportParamBo, HttpServletResponse response) {
        List<ClassroomReserve> list = classroomReserveService.selectList(reportParamBo);
        ExcelUtil.exportExcel(list, "约课清单", ClassroomReserve.class, response);
    }

}
