package com.baidu.health.service;

import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    /**
     * 上传Excel文件批量导入
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList) throws BusinessException;

    /**
     * 通过月份模糊查询当月预约设置信息集合
     * @param month
     * @return
     */
    List<Map<String, Integer>> findOrderSettingByMonth(String month);

    /**
     * 根据当前日期编辑或者添加可预约人数
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
