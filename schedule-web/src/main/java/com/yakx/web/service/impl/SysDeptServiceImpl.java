package com.yakx.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.constant.CacheNames;
import com.yakx.common.constant.UserConstants;
import com.yakx.common.exception.ServiceException;
import com.yakx.common.helper.DataBaseHelper;
import com.yakx.common.utils.BeanCopyUtils;
import com.yakx.common.utils.CacheUtil;
import com.yakx.common.utils.StringUtils;
import com.yakx.common.utils.TreeBuildUtils;
import com.yakx.web.domain.SysDept;
import com.yakx.web.domain.bo.SysDeptBo;
import com.yakx.web.domain.system.StuRole;
import com.yakx.web.domain.vo.SysDeptVo;
import com.yakx.web.mapper.StuRoleMapper;
import com.yakx.web.mapper.SysDeptMapper;
import com.yakx.web.service.ISysDeptService;
import com.yakx.web.service.StuRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        LambdaQueryWrapper<SysDept> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysDept::getDelFlag, "0")
            .eq(ObjectUtil.isNotNull(dept.getDeptId()), SysDept::getDeptId, dept.getDeptId())
            .eq(ObjectUtil.isNotNull(dept.getParentId()), SysDept::getParentId, dept.getParentId())
            .like(StringUtils.isNotBlank(dept.getDeptName()), SysDept::getDeptName, dept.getDeptName())
            .eq(StringUtils.isNotBlank(dept.getStatus()), SysDept::getStatus, dept.getStatus())
            .orderByAsc(SysDept::getParentId)
            .orderByAsc(SysDept::getOrderNum);
        return baseMapper.selectDeptList(lqw);
    }

    @Override
    public List<Tree<Long>> selectDeptTreeList(SysDept dept) {
        // 只查询未禁用部门
        dept.setStatus(UserConstants.DEPT_NORMAL);
        List<SysDept> depts = this.selectDeptList(dept);
        return buildDeptTreeSelect(depts);
    }

    @Override
    public List<Tree<Long>> buildDeptTreeSelect(List<SysDept> depts) {
        if (CollUtil.isEmpty(depts)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(depts, (dept, tree) ->
            tree.setId(dept.getDeptId())
                .setParentId(dept.getParentId())
                .setName(dept.getDeptName())
                .setWeight(dept.getOrderNum()));
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        return null;
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public SysDept selectDeptById(Long deptId) {
        SysDept dept = baseMapper.selectById(deptId);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysDept parentDept = baseMapper.selectOne(new LambdaQueryWrapper<SysDept>()
            .select(SysDept::getDeptName).eq(SysDept::getDeptId, dept.getParentId()));
        dept.setParentName(ObjectUtil.isNotNull(parentDept) ? parentDept.getDeptName() : null);
        return dept;
    }

    @Override
    public long selectNormalChildrenDeptById(Long deptId) {
        return 0;
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysDept>()
            .eq(SysDept::getParentId, deptId));
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return false;
    }

    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysDept>()
            .eq(SysDept::getDeptName, dept.getDeptName())
            .eq(SysDept::getParentId, dept.getParentId())
            .ne(ObjectUtil.isNotNull(dept.getDeptId()), SysDept::getDeptId, dept.getDeptId()));
        return !exist;
    }

    @Override
    public void checkDeptDataScope(Long deptId) {

    }

    @Override
    public int insertDept(SysDept dept) {
        SysDept info = baseMapper.selectById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + StringUtils.SEPARATOR + dept.getParentId());
        return baseMapper.insert(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#dept.deptId")
    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = baseMapper.selectById(dept.getParentId());
        SysDept oldDept = baseMapper.selectById(dept.getDeptId());
        if (ObjectUtil.isNotNull(newParentDept) && ObjectUtil.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + StringUtils.SEPARATOR + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
            && !StringUtils.equals(UserConstants.DEPT_NORMAL, dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        baseMapper.update(null, new LambdaUpdateWrapper<SysDept>()
            .set(SysDept::getStatus, UserConstants.DEPT_NORMAL)
            .in(SysDept::getDeptId, Arrays.asList(deptIds)));
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
        List<SysDept> list = new ArrayList<>();
        for (SysDept child : children) {
            SysDept dept = new SysDept();
            dept.setDeptId(child.getDeptId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(dept);
        }
        if (CollUtil.isNotEmpty(list)) {
            for(SysDept dept: list){
                this.baseMapper.updateById(dept);
            }

            list.forEach(dept -> CacheUtil.evict(CacheNames.SYS_DEPT, dept.getDeptId()));

        }
    }

    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public int deleteDeptById(Long deptId) {
        return baseMapper.deleteById(deptId);
    }
}

