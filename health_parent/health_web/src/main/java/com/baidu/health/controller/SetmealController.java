package com.baidu.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baidu.health.constant.MessageConstant;
import com.baidu.health.entity.PageResult;
import com.baidu.health.entity.QueryPageBean;
import com.baidu.health.entity.Result;
import com.baidu.health.pojo.Setmeal;
import com.baidu.health.service.SetmealService;
import com.baidu.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //TODO 明天学习
        return null;
    }

    /**
     * 添加检查组套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /**
     * 通过id查询套餐信息 回显
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务查询
        Setmeal setmealLzy = new Setmeal();
        setmealLzy.setId(id);
        Setmeal setmeal = setmealService.findById(setmealLzy);
        // 前端要显示图片需要全路径
        // 封装到map中，解决图片路径问题
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("setmeal", setmeal); // formData
        resultMap.put("domain", QiNiuUtils.DOMAIN); // domain
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }

    /**
     * 通过id查询选中的检查组ids
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
    }

    /**
     * 修改套餐
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        // 调用业务服务修改
        setmealService.update(setmeal, checkgroupIds);
        // 响应结果
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(int id){
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
