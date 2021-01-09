package com.baidu.health.service;

import com.baidu.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {

    /**
     * 上传Excel文件批量导入
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

}
