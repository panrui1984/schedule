package com.yakx.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yakx.web.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询菜单
    List<Menu> getMenuByUserId(@Param("userId") Long userId);
    //根据角色id查询菜单
    List<Menu> getMenuByRoleId(@Param("roleId") Long roleId);
    //根据学生id查询菜单
    List<Menu> getMenuByStuId(@Param("stuId") Long stuId);
    //根据教师id查询菜单
    List<Menu> getMenuByTeacherId(@Param("teacherId") Long teacherId);
}
