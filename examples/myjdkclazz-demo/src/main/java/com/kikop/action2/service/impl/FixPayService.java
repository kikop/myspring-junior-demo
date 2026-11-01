package com.kikop.action2.service.impl;

import com.kikop.action2.service.IPayable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FixPayService {

    @Autowired
    @Qualifier("weChatPay")
    private IPayable payable;


    public void doPay(double amount) {
        payable.pay(amount);
    }
}
