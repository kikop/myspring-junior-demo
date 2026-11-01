package com.kikop.action2.service.impl;

import com.kikop.action2.service.IPayable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// PayService（策略模式 + List 注入，完整版）
@Service
public class AllPayService {

    // 收集容器中的IPayable实现类,用于一份数据多个出口
    // 1.yth、localaudit
    // 2.yth、szzc、local520
    private final List<IPayable> iPayableList;

    private final Map<String, IPayable> payMap;

    // Spring 自动注入所有 Payable 实现
    public AllPayService(List<IPayable> iPayableList) {
        this.iPayableList = iPayableList;
// // 用 Spring Bean 名
        this.payMap = iPayableList.stream().collect(Collectors.toMap(p -> p.getClass().getSimpleName(), p -> p, (v1, v2) -> v1)); // key 冲突时取第一个
    }

    public void doPayAll(double amount) {
        for (IPayable iPayable : iPayableList) {
            iPayable.pay(amount);
        }
    }

    /**
     * 按类型调用
     */
    public void doPayByType(String type, double amount) {
        IPayable payable = payMap.get(type);
        if (payable == null) {
            throw new IllegalArgumentException("不支持的支付方式：" + type);
        }
        payable.pay(amount);
    }
}
