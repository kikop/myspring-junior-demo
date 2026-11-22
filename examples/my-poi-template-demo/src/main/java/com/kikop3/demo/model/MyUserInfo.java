package com.kikop3.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyUserInfo {

    private String name;
    private String phone;
    private String address;
}
