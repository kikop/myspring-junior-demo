package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.RowStyle;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * feature:tableRenderData
 */
public class MyTableRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/mytablerenderdatademo.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_mytablerenderdatademo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Map<String, Object> data = new HashMap<String, Object>() {{
            put("title", Texts.of("kikop").create());

            // 一个2行2列的表格
            put("table0", Tables.of(new String[][]{new String[]{"00", "01"}, new String[]{"10", "11"}}).border(BorderStyle.DEFAULT).create());

            // 第0行居中且背景为蓝色的表格
            RowRenderData row0 = Rows.of("姓名", "学历").textColor("FFFFFF").bgColor("4472C4").center().create();
            RowRenderData row1 = Rows.create("李四", "博士");
            put("table1", Tables.create(row0, row1));

            // 合并第1行所有单元格的表格
            RowStyle rowStyle = new RowStyle();
            rowStyle.setHeight(40); // 单位:cm
            RowRenderData row0_h = Rows.of("列0", "列1", "列2").center().bgColor("4472C4").rowStyle(rowStyle).create();
            RowRenderData row1_h = Rows.create("没有数据", null, null);
            // 第一行 begin with 0
            MergeCellRule rule = MergeCellRule.builder()
                    // from: cell(1,0) to cell(1,2)
                    .map(MergeCellRule.Grid.of(1, 1), MergeCellRule.Grid.of(1, 2))
                    .build();
            put("table3", Tables.of(row0_h, row1_h)
                    .mergeRule(rule)
                    .create());

        }};


        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
