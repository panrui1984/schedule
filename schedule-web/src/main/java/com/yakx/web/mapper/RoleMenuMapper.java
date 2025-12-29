package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yakx.web.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //保存角色的权限
    void assignSave(@Param("roleId") Long roleId, @Param("menuList") List<Long> menuList);
}
