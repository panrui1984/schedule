package com.yakx.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yakx.common.core.domain.R;
import com.yakx.common.utils.ResultUtils;
import com.yakx.common.utils.ResultVo;
import com.yakx.common.utils.StringUtils;
import com.yakx.web.domain.bo.ClassParmBo;
import com.yakx.web.domain.Clsases;
import com.yakx.web.service.ClsasesService;
import com.yakx.web.domain.vo.SelectOptionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/classes")
public class ClsasesController {

    private final ClsasesService clsasesService;

    //新增
    @PostMapping
    public R<Void> add(@RequestBody Clsases clsases){
        boolean save = clsasesService.save(clsases);
        if(save){
            return R.ok("新增成功");
        }
        return R.fail("新增失败!");
    }

    //编辑
    @PutMapping
    public R<Void> edit(@RequestBody Clsases clsases){
        boolean save = clsasesService.updateById(clsases);
        if(save){
            return R.ok("编辑成功");
        }
        return R.fail("编辑失败!");
    }

    //删除
    @DeleteMapping("/{classId}")
    public R<Void> delete(@PathVariable("classId") Long classId){
        boolean b = clsasesService.removeById(classId);
        if(b){
            return R.ok("删除成功");
        }
        return R.fail("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public R<IPage<Clsases>> list(ClassParmBo parm){
        //构造分页对象
        IPage<Clsases> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<Clsases> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getClassName())){
            query.lambda().like(Clsases::getClassName,parm.getClassName());
        }
        IPage<Clsases> list = clsasesService.page(page, query);
        return R.ok("查询成功",list);
    }

    //列表
    @GetMapping("/listClass")
    public R<List<SelectOptionVo>> list(){
        List<Clsases> list = clsasesService.list();
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item -> {
                    SelectOptionVo option = new SelectOptionVo();
                    option.setValue(item.getClassId());
                    option.setLabel(item.getClassName());
                    selectOptions.add(option);
                });
        return R.ok("查询成功",selectOptions);
    }

    //班级
    @GetMapping("/getClassList")
    public R<List<SelectOptionVo>> getClassList(){
        List<Clsases> list = clsasesService.list();
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
            .forEach(item->{
                SelectOptionVo option = new SelectOptionVo();
                option.setValue(item.getClassId());
                option.setLabel(item.getClassName());
                selectOptions.add(option);
            });
        return R.ok("查询成功",selectOptions);
    }
}
