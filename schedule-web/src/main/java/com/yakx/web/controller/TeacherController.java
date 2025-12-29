package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.annotation.Log;
import com.yakx.common.core.domain.R;
import com.yakx.common.core.domain.model.LoginUser;
import com.yakx.common.enums.BusinessType;
import com.yakx.common.helper.LoginHelper;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.bo.TeacherParmBo;
import com.yakx.web.domain.vo.SelectOptionVo;
import com.yakx.web.service.TeacherService;
import com.yakx.web.domain.system.TeacherRole;
import com.yakx.web.service.TeacherRoleService;
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
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherRoleService teacherRoleService;

    @Log(title = "教师", businessType = BusinessType.IMPORT)
    @PostMapping
    public R<Void> add(@RequestBody Teacher teacher){
        //学号重复判断
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.lambda().eq(Teacher::getTeacherNum,teacher.getTeacherNum());
        Teacher one = teacherService.getOne(query);
        if(one != null){
            return R.fail("教师编号被占用!");
        }
        //设置密码
        teacher.setPassword(DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()));
        teacherService.addTeacher(teacher);
        return R.ok("新增成功！");
    }

    //编辑
    @Log(title = "教师管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody Teacher teacher){
        //学号重复判断
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.lambda().eq(Teacher::getTeacherNum,teacher.getTeacherNum());
        Teacher one = teacherService.getOne(query);
        if(one != null && !teacher.getTeacherId().equals(one.getTeacherId())){
            return R.fail("教师编号被占用!");
        }
        teacherService.editTeacher(teacher);
        return R.ok("编辑成功！");
    }

    //删除
    @DeleteMapping("/{teacherId}")
    public R<Void> delete(@PathVariable("teacherId") Long teacherId){
        teacherService.deleteTeacher(teacherId);
        return R.ok("删除成功！");
    }

    //列表查询
    @GetMapping("/list")
    public R<IPage<Teacher>> getList(TeacherParmBo parm){
        IPage<Teacher> list = teacherService.getList(parm);
        return R.ok("查询成功",list);
    }

    //根据教师id查询教师信息
    @GetMapping("/getTeacher")
    public R<Teacher> getTeacher(Long teacherId){
        Teacher teacher = teacherService.getById(teacherId);
        QueryWrapper<TeacherRole> query = new QueryWrapper<>();
        query.lambda().eq(TeacherRole::getTeacherId,teacherId);
        TeacherRole one = teacherRoleService.getOne(query);
        teacher.setRoleId(one.getRoleId());
        return R.ok("查询成功",teacher);
    }

    //教师重置密码
    @PostMapping("/resetPassword")
    public R<Void> resetPassword(@RequestBody Teacher teacher){
        //设置密码
        teacher.setPassword(DigestUtils.md5DigestAsHex("666666".getBytes()));
        teacherService.updateById(teacher);
        return R.ok("重置密码成功！");
    }

    //教师
    @GetMapping("/getTeachers")
    public R<List<SelectOptionVo>> getTeachers(){
        LoginUser loginUser = LoginHelper.getLoginUser();
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        if( loginUser.getUserType().equals("2")){
            //如果是老师本人，直接返回本人列表
            SelectOptionVo option = new SelectOptionVo();
            option.setValue(loginUser.getUserId());
            option.setLabel(loginUser.getUsername());
            selectOptions.add(option);
            return R.ok("查询成功",selectOptions);
        }
        List<Teacher> list = teacherService.list();

        Optional.ofNullable(list).orElse(new ArrayList<>())
            .forEach(item->{
                SelectOptionVo option = new SelectOptionVo();
                option.setValue(item.getTeacherId());
                option.setLabel(item.getTeacherName());
                selectOptions.add(option);
            });
        return R.ok("查询成功",selectOptions);
    }

}
