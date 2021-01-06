package com.baidu.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baidu.health.constant.MessageConstant;
import com.baidu.health.entity.Result;
import com.baidu.health.pojo.CheckGroup;
import com.baidu.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    // 查询所有的检查组
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> CheckGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,CheckGroupList);
    }

    // TODO 检查组分页查询 新知识

    //添加检查组
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //根据id查询检查组信息回显
    @GetMapping("/findById")
    public Result findById(int checkGroupId){
        // 调用业务服务
        //TODO 使用懒操作
        CheckGroup LzyCheckGroup = new CheckGroup();
        LzyCheckGroup.setId(checkGroupId);
        CheckGroup checkGroup = checkGroupService.findById(LzyCheckGroup);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    //根据检查组id查询选中的检查项 并回显数据
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int checkGroupId){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        // TODO 需要自定义消息 随便写的
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    //修改
    @PostMapping("/update")
    public Result update (@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        // TODO 需要自定义消息 我随便写的
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //删除检查组 为什么预习资料写的是post请求
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务删除
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
