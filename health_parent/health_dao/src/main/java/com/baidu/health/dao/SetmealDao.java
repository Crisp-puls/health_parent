package com.baidu.health.dao;

import com.baidu.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetmealDao {
    /**
     *添加套餐信息 并且返回id
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查项关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 根据name查询套餐 校验添加修改是否有重复
     * @param setmeal
     * @return
     */
    Setmeal findByName(Setmeal setmeal);

    /**
     * 根据编码查询套餐 校验添加修改是否有重复
     * @param setmeal
     * @return
     */
    Setmeal findByCode(Setmeal setmeal);
}
