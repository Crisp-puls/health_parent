package com.baidu.health.dao;

import com.baidu.health.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {
    /**
     *根据日期查询是否有该条数据 有则返回该日数据
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     *修改该日的可预约人数
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     *添加该日的日期和可预约人数
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);
}
