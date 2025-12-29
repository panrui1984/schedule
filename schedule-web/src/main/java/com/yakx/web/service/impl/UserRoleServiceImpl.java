package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.system.UserRole;
import com.yakx.web.mapper.UserRoleMapper;
import com.yakx.web.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
