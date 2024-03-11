package com.example.pay.controller;

import com.example.pay.pojo.PayInfo;
import com.example.pay.service.impl.PayServiceImpl;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private WxPayConfig wxPayConfig;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum) {
        PayResponse payResponse = payService.create(orderId, amount, bestPayTypeEnum);
        Map map = new HashMap();
        map.put("codeUrl", payResponse.getCodeUrl());
        map.put("orderId", orderId);
        map.put("returnUrl", wxPayConfig.getReturnUrl());
        return new ModelAndView("create", map);
    }

    @PostMapping("/notify")
    @ResponseBody //将java对象转为json格式时加此注解，如果是像上面的create方法，返回的是ModelAndView，不需要加此注解
    public String asyncNotify(@RequestBody String notifyData) {
         return payService.asyncNotify(notifyData);
    }

    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam("orderId") String orderId) {
        log.info("查询支付记录（通过订单号）...");
        return payService.queryByOrderId(orderId);
    }
}
