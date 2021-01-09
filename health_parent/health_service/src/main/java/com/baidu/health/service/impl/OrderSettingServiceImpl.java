package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.baidu.health.dao.OrderSettingDao;
import com.baidu.health.exceptions.BusinessException;
import com.baidu.health.pojo.OrderSetting;
import com.baidu.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
        // 先判断orderSettingList是否为空 isEmpty为空返回true !不会为空进入
        if (!CollectionUtils.isEmpty(orderSettingList)) {
            // 遍历导入的预约设置信息List<OrderSetting>
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (OrderSetting orderSetting : orderSettingList) {
                // 根据拿到的单个日 中的日期查询数据库是否有这个日期
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                // 不为空则修改该日的信息
                if (null != osInDB) {
                    // 传入需要修改的可预约人数 不能小于已预约的人数
                    if (osInDB.getReservations() > orderSetting.getNumber()) {
                        // 数据库中的已经预约大于修改的可预约人数就报错
                        throw new BusinessException(sdf.format(orderSetting.getOrderDate()) + ": 最大可预约数不能小于已预约人数");
                    }else if (osInDB.getReservations() < orderSetting.getNumber()){
                        // 修改的可预约数大于已预约数 则可以修改该日信息
                        orderSettingDao.updateNumber(orderSetting);
                    }else {
                        // 修改的可预约人数等于已预约人数时不做修改让他跳出方法
                        return;
                    }
                } else {
                // 为空 走到这里说明没有查询到数据则添加数据
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 通过月份查询可预约人数和已预约人数
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> findOrderSettingByMonth(String month) {
        // 添加上模糊查询的条件
        month+="%";
        return orderSettingDao.findOrderSettingByMonth(month);
    }

    /**
     * 根据当前日期编辑可预约人数或者添加可预约人数和日期
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 根据当前日期查询该日期是否存在 存在则是编辑可预约人数不存在则是添加可预约人数和日期
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        // 不为空则修改该日的可预约人数
        if (null != osInDB) {
            // 传入需要修改的可预约人数 不能小于已预约的人数
            if (osInDB.getReservations() > orderSetting.getNumber()) {
                // 数据库中的已经预约大于修改的可预约人数就报错
                throw new BusinessException(sdf.format(orderSetting.getOrderDate()) + ": 最大可预约数不能小于已预约人数");
            }else if (osInDB.getReservations() < orderSetting.getNumber()){
                // 修改的可预约数大于已预约数 则可以修改该日信息
                orderSettingDao.updateNumber(orderSetting);
            }else {
                // 修改的可预约人数等于已预约人数时不做修改让他跳出方法
                return;
            }
        } else {
            // 为空 走到这里说明没有查询到数据则添加数据
            orderSettingDao.add(orderSetting);
        }
    }
}
