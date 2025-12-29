package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yakx.web.domain.vo.ReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper extends BaseMapper<ReportVo> {

    List<ReportVo> getList(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

}
