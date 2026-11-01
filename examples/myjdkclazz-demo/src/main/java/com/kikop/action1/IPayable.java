package com.kikop.action1;

// 定义“能力”或“规范”
public interface IPayable {

    // 接口中的方法默认 public abstract
    // 如实战中的 生成bill文件、上传文件到sfpt、
    // 给所有实现类提供一个“可选的、通用的默认实现,不同继承树的类都能用
    // 定义工具方法
    void pay(double amount);

    // JDK 8+ 可以有默认方法
    default void log() {
        System.out.println("支付日志已记录");
    }
}
