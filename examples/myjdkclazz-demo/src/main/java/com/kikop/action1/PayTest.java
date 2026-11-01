package com.kikop.action1;

public class PayTest {
    public static void main(String[] args) {
        IPayable pay1 = new WeChatPay("张三");
        pay1.pay(100);
    }
}
