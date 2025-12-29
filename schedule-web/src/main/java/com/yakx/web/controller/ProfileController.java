package com.yakx.web.controller;

import com.yakx.common.annotation.Log;
import com.yakx.common.core.domain.R;
import com.yakx.common.core.domain.model.LoginUser;
import com.yakx.common.enums.BusinessType;
import com.yakx.common.helper.LoginHelper;
import com.yakx.web.domain.bo.profile.SysUserPasswordBo;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.system.User;
import com.yakx.web.domain.vo.ProfileVo;
import com.yakx.web.service.RoleService;
import com.yakx.web.service.TeacherService;
import com.yakx.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final TeacherService teacherService ;

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping("/profile")
    public R<ProfileVo> profile() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if(loginUser.getUserType().equals("3")){
            //如果是管理员
            User user =  userService.getById(LoginHelper.getUserId());
            ProfileVo profileVo = new ProfileVo();
            profileVo.setUserName(user.getUsername());

            profileVo.setEmail(user.getEmail());
            profileVo.setPhone(user.getPhone());
            profileVo.setCreateTime(user.getCreateTime());
            return R.ok(profileVo);
        }
        Teacher teacher = teacherService.getById(LoginHelper.getUserId());
        ProfileVo profileVo = new ProfileVo();
        profileVo.setUserName(teacher.getTeacherName());
        profileVo.setUserNum(teacher.getTeacherNum());
        profileVo.setEmail(teacher.getEmail());
        profileVo.setPhone(teacher.getPhone());
      //  profileVo.setCreateTime(teacher.getCreateTime());
        profileVo.setTeacher(teacher);
     //   profileVo.setRoleGroup(userService.selectUserRoleGroup(user.getUserName()));
      //  profileVo.setPostGroup(userService.selectUserPostGroup(user.getUserName()));
        return R.ok(profileVo);
    }



    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/profile/updatePwd")
    public R<Void> updatePwd(@Validated @RequestBody SysUserPasswordBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if(loginUser.getUserType().equals("3")){
            //管理员
            User user =  userService.getById(LoginHelper.getUserId());
            String password = user.getPassword();
            if( !DigestUtils.md5DigestAsHex(bo.getOldPassword().getBytes()).equals(password)){
                return R.fail("修改密码失败，旧密码错误");
            }
            if( DigestUtils.md5DigestAsHex(bo.getNewPassword().getBytes()).equals(password)){
                return R.fail("新密码不能与旧密码相同");
            }
            if (userService.resetUserPwd(user.getUserId(), DigestUtils.md5DigestAsHex(bo.getNewPassword().getBytes())) > 0) {
                return R.ok();
            }
            return R.fail("修改密码异常，请联系管理员");
        }


        Teacher teacher = teacherService.getById(LoginHelper.getUserId());
        String password = teacher.getPassword();
        if( !DigestUtils.md5DigestAsHex(bo.getOldPassword().getBytes()).equals(password)){
            return R.fail("修改密码失败，旧密码错误");
        }
        if( DigestUtils.md5DigestAsHex(bo.getNewPassword().getBytes()).equals(password)){
            return R.fail("新密码不能与旧密码相同");
        }

        if (teacherService.resetTeacherPwd(teacher.getTeacherId(), DigestUtils.md5DigestAsHex(bo.getNewPassword().getBytes())) > 0) {
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

}
