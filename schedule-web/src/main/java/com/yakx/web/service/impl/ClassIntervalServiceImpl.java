package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.web.domain.ClassInterval;
import com.yakx.web.mapper.ClassIntervalMapper;
import com.yakx.web.service.ClassIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClassIntervalServiceImpl  extends ServiceImpl<ClassIntervalMapper, ClassInterval> implements ClassIntervalService {


}
