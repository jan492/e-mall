package com.example.pay.service;

import com.example.pay.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

public interface IPayService {
    /**
     * 创建、发起支付
     *
     * @param orderId 订单id
     * @param amount  支付金额
     * @return
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    String asyncNotify(String notifyData);

    /**
     * 查询支付记录（通过订单号）
     *
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}
