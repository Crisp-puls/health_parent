package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.dao.UserDao;
import com.baidu.health.pojo.User;
import com.baidu.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名查询用户角色权限
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
