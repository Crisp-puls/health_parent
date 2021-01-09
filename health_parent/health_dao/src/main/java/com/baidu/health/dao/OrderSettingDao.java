package com.baidu.health.dao;

import com.baidu.health.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {
    /**
     *
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     *
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     *
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);
}
