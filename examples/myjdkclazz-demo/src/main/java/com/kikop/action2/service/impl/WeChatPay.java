package com.kikop.action2.service.impl;

import com.kikop.action2.service.AbstractPay;
import org.springframework.stereotype.Component;

@Component("weChatPay")
public class WeChatPay  extends AbstractPay {
    public WeChatPay() {
        super("微信用户");
    }

    @Override
    protected void doPay(double amount) {
        System.out.println("微信支付：" + amount + " 元");
    }
}
