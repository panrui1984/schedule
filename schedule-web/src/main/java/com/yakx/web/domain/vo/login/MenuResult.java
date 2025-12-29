package com.yakx.web.domain.vo.login;

import com.yakx.web.domain.vo.RouterVO;
import lombok.Data;

import java.util.List;

@Data
public class MenuResult {
    //菜单数据
    private List<RouterVO> menuList;
    //权限字段
    private Object[] authList;
}
