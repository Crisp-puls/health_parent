package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.controller.OrderSettingService;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 上传Excel文件批量导入
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        //先判断orderSettingList是否为空
        if (!orderSettingList.isEmpty()&&orderSettingList.size()>0) {
            //遍历orderSettingList
            for (OrderSetting orderSetting : orderSettingList) {
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                if (null!=osInDB){
                    // 数据库存在这个预约设置, 已预约数量不能大于可预约数量
                    if(osInDB.getReservations() > orderSetting.getNumber()){
                        throw new BusinessException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                    }
                    orderSettingDao.updateNumber(orderSetting);
                }else{
                    // 不存在
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
}
