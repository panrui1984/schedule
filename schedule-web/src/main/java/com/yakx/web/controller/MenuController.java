package com.yakx.web.controller;

import com.yakx.common.core.domain.R;
import com.yakx.common.utils.ResultUtils;
import com.yakx.common.utils.ResultVo;
import com.yakx.web.domain.Menu;
import com.yakx.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public R<Void> add(@RequestBody Menu menu) {
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        boolean save = menuService.save(menu);
        if (save) {
            return R.ok("新增成功!");
        }
        return R.fail("新增失败!");
    }

    //编辑
    @PutMapping
    public R<Void> edit(@RequestBody Menu menu) {
        menu.setUpdateTime(new Date());
        boolean save = menuService.updateById(menu);
        if (save) {
            return R.ok("编辑成功!");
        }
        return R.fail("编辑失败!");
    }

    //删除
    @DeleteMapping("/{menuId}")
    public R<Void> deleteMenu(@PathVariable("menuId") Long menuId) {
        menuService.deleteMenu(menuId);
        return R.ok("删除成功!");
    }

    @GetMapping("/list")
    public R<List<Menu>> getList(){
        List<Menu> list = menuService.getList();
        return R.ok("查询成功",list);
    }

    //上级菜单树数据
    @GetMapping("/parent")
    public R<List<Menu>> getParentList(){
        List<Menu> list = menuService.parentList();
        return R.ok("查询成功",list);
    }
}
