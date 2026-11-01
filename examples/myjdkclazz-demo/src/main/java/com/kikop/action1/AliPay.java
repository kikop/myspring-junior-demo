package com.kikop.action1;

public class AliPay  extends AbstractPay{

    public AliPay(String payer) {
        super(payer);
    }

    @Override
    public void validate() {
        System.out.println("支付宝支付参数校验通过");
    }

    @Override
    public void pay(double amount) {
        validate();
        System.out.println("使用支付宝支付：" + amount + " 元");
        log();
    }


}
