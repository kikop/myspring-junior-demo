package com.kikop.action1;

// 抽取公共逻辑，不能实例化
public abstract class AbstractPay implements IPayable {

    // 1.可以有成员变量
    protected String payer;

    public AbstractPay(String payer) {
        this.payer = payer;
    }

    // 2.可以有抽象方法 & 普通方法
    // 抽象方法（子类必须实现）
    public abstract void validate();

    // 3.可以有抽象方法 & 普通方法
    // 公共方法（子类可复用）
    // 只有子类能用
    public void printInfo() {
        System.out.println("支付人：" + payer);
    }
}
