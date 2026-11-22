package com.kikop3.demo.model;

public class AddrModel {
    private String addr;
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public AddrModel(String addr, String province) {
        this.addr = addr;
        this.province=province;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
