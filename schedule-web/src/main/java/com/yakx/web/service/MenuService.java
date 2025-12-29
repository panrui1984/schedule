package com.yakx.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.Menu;

import java.util.List;


public interface MenuService extends IService<Menu> {
    //删除
    void deleteMenu(Long menuId);
    //列表
    List<Menu> getList();
    //上级树数据
    List<Menu> parentList();
     //根据用户id查询菜单
    List<Menu> getMenuByUserId(Long userId);
    //根据学生id查询菜单
    List<Menu> getMenuByStuId(Long stuId);
    //根据教师id查询菜单
    List<Menu> getMenuByTeacherId(Long teacherId);
    //根据角色id查询菜单
    List<Menu> getMenuByRoleId(Long roleId);
}
