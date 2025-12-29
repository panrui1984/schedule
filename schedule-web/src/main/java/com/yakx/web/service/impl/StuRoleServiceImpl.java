package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.system.StuRole;
import com.yakx.web.mapper.StuRoleMapper;
import com.yakx.web.service.StuRoleService;
import org.springframework.stereotype.Service;

@Service
public class StuRoleServiceImpl extends ServiceImpl<StuRoleMapper, StuRole> implements StuRoleService {
}
