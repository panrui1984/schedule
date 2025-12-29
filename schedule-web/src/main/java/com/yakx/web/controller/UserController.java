package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.PageQuery;
import com.yakx.common.core.domain.R;
import com.yakx.web.domain.system.Role;
import com.yakx.web.service.RoleService;
import com.yakx.web.domain.vo.SelectOptionVo;
import com.yakx.web.domain.bo.UserPageParmBo;
import com.yakx.web.domain.system.User;
import com.yakx.web.service.UserService;
import com.yakx.web.domain.system.UserRole;
import com.yakx.web.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yakx
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    //新增
    @PostMapping
    public R<Void> add(@RequestBody User user) {
        //根据用户名查询
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,user.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User one = userService.getOne(query);
        if(one != null){
            return R.fail("用户名被占用!");
        }
        userService.addUser(user);
        return R.ok("新增成功!");
    }

    //编辑
    @PutMapping
    public R<Void> edit(@RequestBody User user) {
        //根据用户名查询
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,user.getUsername());
        User one = userService.getOne(query);
        if(one != null && one.getUserId() != user.getUserId()){
            return R.fail("用户名被占用!");
        }
        userService.editUser(user);
        return R.ok("编辑成功!");
    }

    //删除
    @DeleteMapping("/{userId}")
    public R<Void> delete(@PathVariable("userId") Long userId) {
        userService.deletUser(userId);
        return R.ok("删除成功!");
    }

    //列表查询
    @GetMapping("/list")
    public R<IPage<User>> getList(UserPageParmBo parm,  PageQuery pageQuery) {
        IPage<User> list = userService.getUserList(parm);
        return R.ok("查询成功", list);
    }

    //角色列表查询
    @GetMapping("/role")
    public R<List<SelectOptionVo>> getRole() {
        List<Role> list = roleService.list();
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectOptionVo option = new SelectOptionVo();
                    option.setValue(item.getRoleId());
                    option.setLabel(item.getRoleName());
                    selectOptions.add(option);
                });
        return R.ok("查询成功", selectOptions);
    }
    //根据id查询用户信息
    @GetMapping("/getUser")
    public R<User>  getUserById(Long userId){
        User user = userService.getById(userId);
        //查角色id
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.lambda().eq(UserRole::getUserId,userId);
        UserRole one = userRoleService.getOne(query);
        user.setRoleId(one.getRoleId());
        return R.ok("查询成功",user);
    }


    //重置密码
    @PutMapping("/editPassword")
    public R<Void> editPassword(@RequestBody User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        boolean reset = userService.resetPwd(user) > 0 ? true : false ;
        if(reset){
            return R.ok("密码修改成功!");
        }
        return R.fail("密码修改失败!");
    }
}
