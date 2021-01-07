package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.dao.SetmealDao;
import com.baidu.health.entity.PageResult;
import com.baidu.health.entity.QueryPageBean;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.Setmeal;
import com.baidu.health.service.SetmealService;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    private SetmealDao setmealDao;
    /**
     * 添加检查组套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        Setmeal findByNameSetmeal =setmealDao.findByName(setmeal);
        Setmeal findByCodeSetmeal =setmealDao.findByCode(setmeal);
        if (null != findByNameSetmeal) {
            throw new BusinessException("添加的套餐名称相同！！");
            //TODO 设置常量类的返回消息
        }
        if (null != findByCodeSetmeal) {
            //TODO 设置常量类的返回消息
            throw new BusinessException("添加的套餐编码相同！！");
        }
        //先添加套餐信息 并且返回id 给下面建立关系时使用
        setmealDao.add(setmeal);
        //添加后使用SQL语句获取 添加的套餐id
        Integer setmealId =setmeal.getId();
        //添加检查组与套餐的关系 对套餐来说 套餐是1检查组是多
        //判断用户是否勾选检查组
        if (null !=checkgroupIds){
            //不为空则遍历勾选的检查组
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {

        return null;
    }
}
