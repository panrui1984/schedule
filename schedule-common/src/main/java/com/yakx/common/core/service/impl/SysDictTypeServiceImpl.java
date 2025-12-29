package com.yakx.common.core.service.impl;

import com.yakx.common.core.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 */
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl implements DictService {


    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {
        return null;
    }

    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {
        return null;
    }

    @Override
    public Map<String, String> getAllDictByDictType(String dictType) {
        return null;
    }
}
