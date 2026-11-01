package com.kikop.action2.service.impl;

import com.kikop.action2.service.AbstractPay;
import org.springframework.stereotype.Component;

@Component("aliPay")
public class AliPay extends AbstractPay {
    public AliPay() {
        super("支付宝用户");
    }

    @Override
    protected void doPay(double amount) {
        System.out.println("支付宝用户支付：" + amount + " 元");
    }
}
