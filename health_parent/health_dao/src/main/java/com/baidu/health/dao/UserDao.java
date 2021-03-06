package com.baidu.health.dao;

import com.baidu.health.pojo.User;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/13
 */
public interface UserDao {
    /**
     * 通过用户名查询用户角色权限
     * @param username
     * @return
     */
    User findByUsername(String username);
}
