package com.yakx.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yakx.common.core.domain.R;
import com.yakx.web.domain.ClassRoom;
import com.yakx.web.domain.bo.ClassromListParmBo;
import com.yakx.web.domain.bo.classsroom.MaintenanceBo;
import com.yakx.web.domain.vo.SelectOptionVo;
import com.yakx.web.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author yakx
 * @description 实验室管理
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    private final ClassRoomService classRoomService;

    //新增
    @PostMapping
    public R<Void> add(@RequestBody ClassRoom classRoom) {
        boolean save = classRoomService.save(classRoom);
        if (save) {
            return R.ok("新增成功!");
        }
        return R.fail("新增失败!");
    }

    //编辑
    @PutMapping
    public R<Void> edit(@RequestBody ClassRoom classRoom) {
        boolean save = classRoomService.updateById(classRoom);
        if (save) {
            return R.ok("编辑成功!");
        }
        return R.fail("编辑失败!");
    }

    //删除
    @DeleteMapping("/{roomId}")
    public R<Void> delete(@PathVariable("roomId") Long roomId) {
        boolean b = classRoomService.removeById(roomId);
        if (b) {
            return R.ok("删除成功!");
        }
        return R.fail("删除失败!");
    }

    //分页查询
    @GetMapping("/list")
    public R<IPage<ClassRoom>> getList(ClassromListParmBo listParm) {
        IPage<ClassRoom> list = classRoomService.getList(listParm);
        return R.ok("查询成功", list);
    }

    //查询全部
    @GetMapping("/all")
    public R<List<ClassRoom>> getAll() {
        return R.ok("查询成功", classRoomService.getAll());
    }

    //下拉列表
    @GetMapping("/getRoomList")
    public R<List<SelectOptionVo>> getRoomList() {
        //查询list
        List<ClassRoom> list = classRoomService.list(new LambdaQueryWrapper<ClassRoom>().orderByAsc(ClassRoom::getOrderNum));
        //组装前端需要的数据格式
        List<SelectOptionVo> selectOptions = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
            .forEach(item -> {
                SelectOptionVo option = new SelectOptionVo();
                option.setValue(item.getRoomId());
                option.setLabel(item.getRoomName());
                selectOptions.add(option);
            });
        return R.ok("查询成功", selectOptions);
    }


    /**
     * 设置实验室维护状态
     */
    @PostMapping("/addMaintenance")
    public R<Void> addMaintenance(@RequestBody MaintenanceBo bo) {
        boolean save = classRoomService.addMaintenance(bo);
        if (save) {
            return R.ok("设置实验室维护状态成功!");
        }
        return R.fail("设置失败!");
    }

    /**
     * 移除实验室维护状态
     */
    @PostMapping("/removeMaintenance")
    public R<Void> removeMaintenance(@RequestBody MaintenanceBo bo) {
        boolean b = classRoomService.removeMaintenance(bo);
        if (b) {
            return R.ok("删除设置实验室维护状态成功!");
        }
        return R.fail("删除设置实验室维护状态失败!");
    }
}
