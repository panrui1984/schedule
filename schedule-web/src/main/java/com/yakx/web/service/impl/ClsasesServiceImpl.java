package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.Clsases;
import com.yakx.web.mapper.ClsasesMapper;
import com.yakx.web.service.ClsasesService;
import org.springframework.stereotype.Service;

/**
 * @author yakx
 */
@Service
public class ClsasesServiceImpl extends ServiceImpl<ClsasesMapper, Clsases> implements ClsasesService {
}
