package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.DocumentRenderPolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feature:动态修改模板并生成结果
 */
public class MyDocumentRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "mytemplate/mydocumentrenderdatademo.docx";
        //  默认当前工作目录相对路径
        String destPath = "myresult/dest_mydocumentrenderdatademo.docx";
        String destPathNew = "myresult/dest_mydocumentrenderdatademoNew.docx";
        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        // 我们把原先的一个文本标签分成一个文本标签和一个表格标签
        // 可先并设置文本标签样式
        // 表格需下面控制
        DocumentRenderData document = Documents.of()
                // ParagraphBuilder
                .addParagraph(Paragraphs.of("{{newtitle}}").left().defaultTextStyle(Style.builder()
                        .buildFontFamily("微软雅黑").buildFontSize(20).build())
                        .create())
                .addParagraph(Paragraphs.of("{{#newtable}}").create())
                .create();
        data.put("title", document);
        data.put("newtitle", "kikop\n\n"); // 文本换行使用 \n 字符。

        // 第0行居中且背景为蓝色的表格
        RowRenderData row0 = Rows.of("姓名", "学历").textBold().textColor("FFFFFF").bgColor("4472C4").center().create();
        // 第1行
        //RowRenderData row1 = Rows.create("李四", "博士");
        RowRenderData row1 = Rows.of("李四", "博士").textColor("000000")
                //.bgColor("4472C4")
        .center().create();
        // 第2行
        //RowRenderData row2 = Rows.create("没有数据", null);
        RowRenderData row2 = Rows.of("没有数据", "").textColor("999999").bgColor("F2F2F2").create();
        // 合并第2行所有单元格的表格,begin with:2
        MergeCellRule rule = MergeCellRule.builder().map(MergeCellRule.Grid.of(2, 0), MergeCellRule.Grid.of(2, 1)).build();
        TableRenderData tableRenderData = Tables.create(row0, row1, row2);
        tableRenderData.setMergeRule(rule);

        data.put("newtable", tableRenderData);

        // compile 编译模板
        // render 渲染数据
        // write 输出到流
        ConfigureBuilder configureBuilder = Configure.builder();
        configureBuilder.bind("title", new DocumentRenderPolicy());

        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same, configureBuilder.build()).render(data);
        template.writeAndClose(new FileOutputStream(destPath));

//        try (XWPFTemplate templateNew = XWPFTemplate.compile(destPath, configureBuilder.build()).render(data)) {
//            templateNew.write(new FileOutputStream(destPathNew));
//        }
        XWPFTemplate templateNew = XWPFTemplate.compile(destPath, configureBuilder.build()).render(data);
        templateNew.writeAndClose(new FileOutputStream(destPathNew));


    }

}
