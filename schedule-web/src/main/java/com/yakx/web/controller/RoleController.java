package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.R;
import com.yakx.web.domain.bo.role.AssignParm;
import com.yakx.web.domain.bo.role.RoleListParm;
import com.yakx.web.domain.vo.AssignVo;
import com.yakx.web.domain.system.Role;
import com.yakx.web.service.RoleService;
import com.yakx.web.domain.bo.SaveAssignBo;
import com.yakx.web.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    private final RoleMenuService roleMenuService;

    //新增
    @PostMapping
    public R<Void> add(@RequestBody Role role){
        boolean save = roleService.save(role);
        if(save){
            return R.ok("查询成功");
        }
        return R.fail("新增失败!");
    }

    //新增
    @PutMapping
    public R<Void> edit(@RequestBody Role role){
        boolean save = roleService.updateById(role);
        if(save){
            return R.ok("编辑成功");
        }
        return R.fail("编辑失败!");
    }

    //删除
    @DeleteMapping("/{roleId}")
    public R<Void> delete(@PathVariable("roleId") Long roleId){
        boolean save = roleService.removeById(roleId);
        if(save){
            return R.ok("删除成功");
        }
        return R.fail("删除失败!");
    }

    //查询列表
    @GetMapping("/list")
    public R<IPage<Role>> getList(RoleListParm listParm){
        IPage<Role> list = roleService.getList(listParm);
        return R.ok("查询成功",list);
    }

    //查询分配权限回显
    @GetMapping("/getAssingShow")
    public R<AssignVo> getAssingShow(AssignParm parm){
        AssignVo vo = roleService.getAssignShow(parm);
        return  R.ok("查询成功",vo);
    }

    //分配权限保存
    @PostMapping("/assignSave")
    public R<Void> assignSave(@RequestBody SaveAssignBo saveAssign){
        roleMenuService.assignSve(saveAssign);
        return R.ok("分配权限成功!");
    }
}
