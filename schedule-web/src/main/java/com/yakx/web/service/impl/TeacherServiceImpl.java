package com.yakx.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.domain.bo.TeacherParmBo;
import com.yakx.web.mapper.TeacherMapper;
import com.yakx.web.service.TeacherService;
import com.yakx.web.domain.system.TeacherRole;
import com.yakx.web.service.TeacherRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 26166
 */
@RequiredArgsConstructor
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    private final TeacherRoleService teacherRoleService;

    @Override
    public IPage<Teacher> getList(TeacherParmBo parm) {
        //构造分页对象
        IPage<Teacher> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造查询条件
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getTeacherName())){
            query.lambda().like(Teacher::getTeacherName,parm.getTeacherName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    @Transactional
    public void addTeacher(Teacher teacher) {
        int insert = this.baseMapper.insert(teacher);
        if(insert >0){
            //设置教师角色
            TeacherRole teacherRole = new TeacherRole();
            teacherRole.setTeacherId(teacher.getTeacherId());
            teacherRole.setRoleId(teacher.getRoleId());
            teacherRoleService.save(teacherRole);
        }
    }

    @Override
    @Transactional
    public void editTeacher(Teacher teacher) {
        int i = this.baseMapper.updateById(teacher);
        if(i >0){
            //删除原来角色
            QueryWrapper<TeacherRole> query = new QueryWrapper<>();
            query.lambda().eq(TeacherRole::getTeacherId,teacher.getTeacherId());
            teacherRoleService.remove(query);
            //设置教师角色
            TeacherRole teacherRole = new TeacherRole();
            teacherRole.setTeacherId(teacher.getTeacherId());
            teacherRole.setRoleId(teacher.getRoleId());
            teacherRoleService.save(teacherRole);
        }
    }

    @Override
    @Transactional
    public void deleteTeacher(Long teacherId) {
        int i = this.baseMapper.deleteById(teacherId);
        if(i>0){
            QueryWrapper<TeacherRole> query = new QueryWrapper<>();
            query.lambda().eq(TeacherRole::getTeacherId,teacherId);
            teacherRoleService.remove(query);
        }
    }

    /**
     * 重置老师密码
     *
     * @param teacherId 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetTeacherPwd(Long teacherId, String password) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<Teacher>()
                .set(Teacher::getPassword, password)
                .eq(Teacher::getTeacherId, teacherId));
    }

}
