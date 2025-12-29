package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.util.MakeTree;
import com.yakx.web.domain.Menu;
import com.yakx.web.mapper.MenuMapper;
import com.yakx.web.service.MenuService;
import com.yakx.web.domain.RoleMenu;
import com.yakx.web.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * @author yakx
 */

@RequiredArgsConstructor
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuService roleMenuService;

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        //删除菜单
        int i = this.baseMapper.deleteById(menuId);
        if(i>0){
            //删除菜单对应的关联
            QueryWrapper<RoleMenu> query = new QueryWrapper<>();
            query.lambda().eq(RoleMenu::getMenuId,menuId);
            roleMenuService.remove(query);
        }
    }

    @Override
    public List<Menu> getList() {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Menu::getOrderNum);
        //获取菜单
        List<Menu> menuList = this.baseMapper.selectList(query);
        //组装树数据
        return  MakeTree.makeMenuTree(menuList, 0L);
    }

    @Override
    public List<Menu> parentList() {
        //只获取目录和菜单
        String[] types = {"0","1"};
        //查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().in(Menu::getType, Arrays.asList(types)).orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(query);
        //构造顶级菜单
        Menu menu = new Menu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("顶级菜单");
        menus.add(menu);
        //构造树形数据
        return MakeTree.makeMenuTree(menus, -1L);
    }

    @Override
    public List<Menu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }

    @Override
    public List<Menu> getMenuByStuId(Long stuId) {
        return this.baseMapper.getMenuByStuId(stuId);
    }

    @Override
    public List<Menu> getMenuByTeacherId(Long teacherId) {
        return this.baseMapper.getMenuByTeacherId(teacherId);
    }

    @Override
    public List<Menu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }
}
