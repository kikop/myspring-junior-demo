package com.kikop.action1;


// 具体实现类（普通公共类）
// 共用原始数据一份,数据上报到系统1,系统2，服务地址不一样
public class WeChatPay extends AbstractPay {

    public WeChatPay(String payer) {
        super(payer);
    }

    @Override
    public void validate() {
        System.out.println("微信支付参数校验通过");
    }

    @Override
    public void pay(double amount) { // upinterface
        validate(); // currentClz
        System.out.println("使用微信支付：" + amount + " 元");
        log(); // upinterface
    }
}
