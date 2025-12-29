package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.RoleMenu;
import com.yakx.web.domain.bo.SaveAssignBo;
import com.yakx.web.mapper.RoleMenuMapper;
import com.yakx.web.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * &#064;description  组装树形菜单
 * @author 26166
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Transactional
    @Override
    public void assignSve(SaveAssignBo saveAssign) {
        //先删除角色原来的权限
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId,saveAssign.getRoleId());
        this.baseMapper.delete(query);
        //新的插入
        this.baseMapper.assignSave(saveAssign.getRoleId(),saveAssign.getList());
    }
}
