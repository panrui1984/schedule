package com.yakx.web.domain.vo;

import com.yakx.web.domain.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AssignVo {
    //当前用户拥有的菜单
    private List<Menu> menuList = new ArrayList<>();
    //被分配的角色拥有的菜单id
    private Object[] checkList;
}