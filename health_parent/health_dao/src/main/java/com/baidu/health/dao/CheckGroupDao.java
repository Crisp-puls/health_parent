package com.baidu.health.dao;

import com.baidu.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    /**
     * 查询所有检查组方法
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加所有检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系 绑定中间表
     * @param checkGroupId 注意要取别名，类型相同 @Param注解
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId")Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    /**
     * 删除时查询是否被套餐使用
     * @param id
     * @return
     */
    int findSetmealCountByCheckGroupId(int id);

    /**
     * 删除关联的中间表
     * @param id
     */
    void deleteCheckGroupCheckItem(int id);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据id查询检查组 回显数据
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
     */
    void update(CheckGroup checkGroup);
}
