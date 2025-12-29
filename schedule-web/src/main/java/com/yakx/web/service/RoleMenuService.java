package com.yakx.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.RoleMenu;
import com.yakx.web.domain.bo.SaveAssignBo;

public interface RoleMenuService extends IService<RoleMenu> {
    void assignSve(SaveAssignBo saveAssign);
}
