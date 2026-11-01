package com.kikop3.demo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyUserInfo {

    private String name;
    private String phone;
    private String address;
}
