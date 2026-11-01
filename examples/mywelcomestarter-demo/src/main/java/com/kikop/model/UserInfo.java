package com.kikop.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class UserInfo {
    List<String> permissions= Collections.emptyList();
}
