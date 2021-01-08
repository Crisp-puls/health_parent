package com.baidu.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.dao.SetmealDao;
import com.baidu.health.entity.PageResult;
import com.baidu.health.entity.QueryPageBean;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.Setmeal;
import com.baidu.health.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        if (queryPageBean.getPageSize()>50 ){
            queryPageBean.setPageSize(50);
        }
        //传入两个数据当前页数和每页条数
        //pageHelper.startPage(1,10);
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 判断是否有条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            // 有查询条件， 使用模糊查询 拼接上%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // page extends arrayList 返回的值 list<Country> list = countryMapper.selectIf(1);
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<Setmeal>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询套餐 回显数据
     * @param setmealLzy
     * @return
     */
    @Override
    public Setmeal findById(Setmeal setmealLzy) {
        return setmealDao.findById(setmealLzy);
    }

    /**
     * 根据id查询被选中的id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    /**
     * 修改套餐信息
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {

        Setmeal findByNameSetmeal = setmealDao.findByName(setmeal);
        Setmeal findByCoedSetmeal = setmealDao.findByCode(setmeal);
        //判断为空说明没有相同的
        if (null != findByNameSetmeal){
            //不为空说明是相同的 传入的项目名在数据库中有
            //判断id是否相同 相同说明是自己的项目名
            if (findByCoedSetmeal.getId() != setmeal.getId()){
                //不同说明不是自己的项目名
                throw new BusinessException("修改的套餐，名称重复！！");
                //TODO 设置常量类的返回消息
            }
        }
        //判断为空说明没有相同的
        if (null != findByCoedSetmeal){
            //不为空说明是相同的 传入的项目名在数据库中有
            //判断id是否相同 相同说明是自己的项目名
            if (findByCoedSetmeal.getId() != setmeal.getId()){
                //不同说明不是自己的项目名
                throw new BusinessException("修改的套餐，编码重复！！");
                //TODO 设置常量类的返回消息
            }
        }
        //执行更新套餐操作
        setmealDao.update(setmeal);
        //删除套餐与检查组的旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加套餐与检查组的新关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 根据id删除套餐信息
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count > 0){
            //该套餐被订单使用了，不能删除
            throw new BusinessException("该套餐被订单使用了，不能删除！");
        }
        //该套餐没有被订单使用 先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        //再删除套餐
        setmealDao.deleteById(id);
    }
}
