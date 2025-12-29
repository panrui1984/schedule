package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.bo.role.RoleListParm;
import com.yakx.web.util.MakeTree;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.Menu;
import com.yakx.web.service.MenuService;
import com.yakx.web.domain.bo.role.AssignParm;
import com.yakx.web.domain.vo.AssignVo;
import com.yakx.web.domain.system.Role;
import com.yakx.web.mapper.RoleMapper;
import com.yakx.web.service.RoleService;
import com.yakx.web.domain.system.User;
import com.yakx.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final UserService userService;

    private final MenuService menuService;
    @Override
    public IPage<Role> getList(RoleListParm listParm) {
        //构造分页对象
        IPage<Role> page = new Page<>();
        page.setSize(listParm.getPageSize());
        page.setCurrent(listParm.getCurrentPage());
        //构造查询条件
        QueryWrapper<Role> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(listParm.getRoleName())){
            query.lambda().like(Role::getRoleName,listParm.getRoleName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public AssignVo getAssignShow(AssignParm parm) {
        //查询当前用户信息
        User user = userService.getById(parm.getUserId());
        //查询用户的菜单
        List<Menu> list = null;
        if(user.getIsAdmin().equals("1")){ //管理员，直接查询所有的菜单
            QueryWrapper<Menu> query = new QueryWrapper<>();
            query.lambda().orderByAsc(Menu::getOrderNum);
            list = menuService.list(query);
        }else{
            list = menuService.getMenuByUserId(parm.getUserId());
        }
        //组装菜单数据
        List<Menu> menuList = MakeTree.makeMenuTree(list, 0L);
        //查询角色对应的菜单
        List<Long> ids = new ArrayList<>();
        List<Menu> roleList = menuService.getMenuByRoleId(parm.getRoleId());
        Optional.ofNullable(roleList).orElse(new ArrayList<>())
                .stream()
                .filter(item ->item != null)
                .forEach(item ->{
                    ids.add(item.getMenuId());
                });
        AssignVo vo = new AssignVo();
        vo.setCheckList(ids.toArray());
        vo.setMenuList(menuList);
        return vo;
    }
}
