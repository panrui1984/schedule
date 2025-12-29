package com.yakx.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yakx.web.domain.bo.UserPageParmBo;
import com.yakx.web.domain.system.User;

public interface UserService extends IService<User>{
    //新增
    void addUser(User user);
    //编辑
    void editUser(User user);
    //删除
    void deletUser(Long userId);

    //修改密码
    int resetPwd(User user);

    int resetUserPwd(Long userId, String password);
    //列表
    IPage<User> getUserList(UserPageParmBo parm);
}
