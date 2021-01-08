package com.baidu.health.service;

import com.baidu.health.entity.PageResult;
import com.baidu.health.entity.QueryPageBean;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    /**
     * 添加检查组套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds)  throws BusinessException;

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐 回显数据
     * @param setmealLzy
     * @return
     */
    Setmeal findById(Setmeal setmealLzy);

    /**
     * 根据id查询被选中的id集合
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 修改套餐信息
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds)  throws BusinessException;

    /**
     * 根据id删除套餐信息
     * @param id
     */
    void deleteById(int id)  throws BusinessException;
}
