package com.kikop.action2.service;

// 接口（定义支付能力）
// 公共工具无状态
public interface IPayable {
    // 定义能力和规范
    // 校验参数、
    // 生成bill文件、
    // sftp的工具方法(初始化、上传、重命名) 这个方法放在哪
    void pay(double amount);
}
