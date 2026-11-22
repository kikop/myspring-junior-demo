package com.kikop3.demo;

import com.kikop3.demo.model.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

// 生成一个 包含所有字段的全参构造器
@AllArgsConstructor
//生成一个 无参构造器
@NoArgsConstructor
public class MyDataModel {
    private String name;
    private String startTime;
    private Author author;
}
