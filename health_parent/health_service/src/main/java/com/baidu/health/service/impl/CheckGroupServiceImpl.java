package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.constant.MessageConstant;
import com.baidu.health.dao.CheckGroupDao;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.CheckGroup;
import com.baidu.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    //添加所有检查组
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //添加完后在做查询 获取id给下面用
        Integer checkGroupId = checkGroup.getId();
        //TODO 添加前先判断 编码和名称CV即可 明天先问在修改在添加上去

        // 遍历检查项id, 添加检查组与检查项的关系（查询id绑定中间表）
        //先判断勾选是否为空
        if(null != checkitemIds){
            // 有钩选
            //遍历出所有已勾选的检查项
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                //检查组的id一个而检查项的id多个
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
        //TODO 分析可能会出问题
    }

   //根据id查询检查组 回显数据
    @Override
    public CheckGroup findById(CheckGroup lzyCheckGroup) {
        return checkGroupDao.findById(lzyCheckGroup);
    }
    //根据检查组id查询选中的检查项id 回显数据
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    //更新检查组
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // TODO 先判断
        // 先执行检查组
        checkGroupDao.update(checkGroup);
        // 删除中间表关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        // 创建新的中间表关系
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    //删除检查组
    @Override
    public void deleteById(int id){
        // 检查 这个检查组是否被套餐使用了
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if(count > 0){
            // 被使用了
            throw new BusinessException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        // 没有被套餐使用,就可以删除数据
        // 先删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        // 删除检查组
        checkGroupDao.deleteById(id);
    }


}
