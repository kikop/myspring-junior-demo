package com.kikop.action2.service;

import org.springframework.beans.factory.annotation.Autowired;

//  抽象类（公共逻辑 + 模板方法）
// 公共业务有状态
public abstract class AbstractPay implements IPayable {

    protected String payer;

//    1.Spring 创建 WeChatPay Bean 时：
//    实例化 WeChatPay
//    调用父类构造器
//    处理父类字段上的 @Autowired
//    注入 SftpService

    // 2.注入发生在子类 Bean 上，不是抽象类本身
    // 3.抽象类不需要被管理，它只需要：


    // 正确位置：SftpUtil / SftpService（独立工具类或组件）
    @Autowired
    protected SftpService sftpService;


    public AbstractPay(String payer) {
        this.payer = payer;
    }

    // 模板方法
    // pay() 固定流程，子类只管 doPay()，validate和log在抽象类中实现
    public void pay(double amount) {
        validate(); // clazz
        doPay(amount); // child clazz
        log(); // current clazz
    }

    protected abstract void doPay(double amount);

    private void validate() {
        System.out.println("[" + payer + "] 参数校验通过");
    }

    private void log() {
        System.out.println("支付日志已记录");
    }
}
