package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yakx.web.domain.SysDept;
import com.yakx.web.domain.vo.SysDeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询部门管理数据
     *
     * @param queryWrapper 查询条件
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);

}
