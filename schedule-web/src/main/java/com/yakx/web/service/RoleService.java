package com.yakx.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.bo.role.AssignParm;
import com.yakx.web.domain.bo.role.RoleListParm;
import com.yakx.web.domain.vo.AssignVo;
import com.yakx.web.domain.system.Role;


public interface RoleService extends IService<Role> {
    IPage<Role> getList(RoleListParm listParm);
    //角色权限的回显
    AssignVo getAssignShow(AssignParm parm);
}
