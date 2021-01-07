package com.baidu.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baidu.health.constant.MessageConstant;
import com.baidu.health.entity.PageResult;
import com.baidu.health.entity.QueryPageBean;
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

    /**
     * 查询所有的检查组 该方法不能直接暴露在外界中
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> CheckGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,CheckGroupList);
    }


    /**
     * 检查组的分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用服务 分页查询
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }


    /**
     * 添加检查组
     * @param checkGroup 检查组信息
     * @param checkitemIds 勾选的检查项id
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 根据id查询检查组信息回显
     * @param checkGroupId 检查组id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int checkGroupId){
        // 调用业务服务
        //TODO 使用懒
        CheckGroup LzyCheckGroup = new CheckGroup();
        LzyCheckGroup.setId(checkGroupId);
        CheckGroup checkGroup = checkGroupService.findById(LzyCheckGroup);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 根据检查组id查询选中的检查项集 并回显数据
     * @param checkGroupId 检查项id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int checkGroupId){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        // TODO 需要自定义消息 随便写的
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /**
     * 修改检查组
     * @param checkGroup 检查组信息
     * @param checkitemIds 需要需要勾选的检查项
     * @return
     */
    @PostMapping("/update")
    public Result update (@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        // TODO 需要自定义消息 我随便写的
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务删除
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
