package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.dao.CheckItemDao;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.CheckItem;
import com.baidu.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //查询所有检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //添加检查项
    //TODO 需不需抛出去异常
    @Override
    public void add(CheckItem checkItem) {
            CheckItem findByNameCheckItem =checkItemDao.findByName(checkItem);
            CheckItem findByCoedCheckItem =checkItemDao.findByCoed(checkItem);
            if (null!=findByNameCheckItem) {
                throw new BusinessException("添加的检查项项目名相同！！");
                //TODO 设置常量类的返回消息
            }
            if (null != findByCoedCheckItem){
                //TODO 设置常量类的返回消息
                throw new BusinessException("添加的检查项目编码相同！！");
            }
            checkItemDao.add(checkItem);
    }
    //删除检查项
    @Override
    public void deleteById(int id){
        //先判断这个检查项是否被检查组使用了
        //调用dao查询检查项的id是否在t_checkgroup_checkitem表中存在记录
        int countByCheckItemId =checkItemDao.CountByCheckItemId(id);
        //被使用了则不能删除
        if (countByCheckItemId>0){
            //建议自定义异常类
            //TODO 设置常量类的返回消息
            throw new BusinessException("删除检查项失败");
        }
        //没使用就可以调用dao删除
        checkItemDao.deleteById(id);
    }

    //根据id查询检查项回显
    @Override
    public CheckItem findById(CheckItem lzyCheckItem) {
        return checkItemDao.findById(lzyCheckItem);
    }

    /*
       改bug
     */

    //更新检查项
    @Override
    public void update(CheckItem checkItem) {
        CheckItem findByNameCheckItem =checkItemDao.findByName(checkItem);
        CheckItem findByCoedCheckItem =checkItemDao.findByCoed(checkItem);
        if (null != findByNameCheckItem) {
            throw new BusinessException("更新的检查项项目名相同！！");
            //TODO 设置常量类的返回消息
        }
        if (null != findByCoedCheckItem){
            //TODO 设置常量类的返回消息
            throw new BusinessException("更新的检查项目编码相同！！");
        }
        checkItemDao.update(checkItem);
    }
}
