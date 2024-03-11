package com.example.mall.service.impl;

import com.example.mall.MallApplicationTests;
import com.example.mall.enums.ResponseEnum;
import com.example.mall.form.ShippingForm;
import com.example.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTests {

    @Autowired
    private ShippingServiceImpl shippingService;

    private final Integer uid = 1;

    private ShippingForm form;

    private Integer shippingId;

    @Before
    public void before() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zjzjz");
        form.setReceiverAddress("DUT");
        form.setReceiverCity("Dalian");
        form.setReceiverMobile("12345678910");
        form.setReceiverPhone("756344876");
        form.setReceiverProvince("Liaoning");
        form.setReceiverDistrict("Shahekou");
        form.setReceiverZip("116024");
        this.form = form;
        add();
    }

    private void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        this.shippingId = responseVo.getData().get("shippingId");
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
}

    @Test
    public void update() {
        form.setReceiverCity("Shenyang");
        ResponseVo responseVo = shippingService.update(uid, shippingId, form);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo responseVo = shippingService.list(uid, 1, 10);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}