package com.baidu.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baidu.health.constant.MessageConstant;
import com.baidu.health.entity.Result;
import com.baidu.health.pojo.CheckItem;
import com.baidu.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //查询所有检查项
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItemList =checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemList);
    }
    //添加检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    //TODO 检查项分页 新知识

    //TODO 是否需要实现懒加载？

    //根据id删除检查项 为什么预习资料的是post请求？改数据库的都需要使用post请求
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    //根据id查询检查项回显
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem lzyCheckItem = new CheckItem();
        lzyCheckItem.setId(id);
        CheckItem checkItem = checkItemService.findById(lzyCheckItem);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }


    //更新
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

}
