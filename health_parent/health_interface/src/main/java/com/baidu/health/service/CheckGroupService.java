package com.baidu.health.service;

import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.CheckGroup;


import java.util.List;

public interface CheckGroupService {

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加检查组
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据id查询检查组回显数据
     * @param lzyCheckGroup
     * @return
     */
    CheckGroup findById(CheckGroup lzyCheckGroup);

    /**
     * 根据检查组id查询选中的检查项id 回显数据
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id) throws BusinessException;


}
