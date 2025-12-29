package com.yakx.common.convert;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.WorkBookUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelLocalTimeConvert implements Converter<LocalTime> {

    @Override
    public Class<String> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<Object> convertToExcelData(LocalTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>((value).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
