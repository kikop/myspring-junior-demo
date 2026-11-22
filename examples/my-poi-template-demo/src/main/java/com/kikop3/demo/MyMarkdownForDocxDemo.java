package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.markdown.MarkdownRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderPolicy;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;
import com.kikop3.demo.model.MyConstDefine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * feature:将Markdown渲染为word文档
 */
public class MyMarkdownForDocxDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = MyConstDefine.myTemplate + "mymarkdownfordocx.docx";

        //  默认当前工作目录相对路径
        String destPath = MyConstDefine.myResult + "dest_mymarkdownfordocx.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        MarkdownRenderData code = new MarkdownRenderData();
        code.setMarkdown(new String(Files.readAllBytes(Paths.get(MyConstDefine.myTemplate + "mytest.md"))));
        code.setStyle(MarkdownStyle.newStyle());

        Map<String, Object> data = new HashMap<>();
        data.put("mymd", code);


        ConfigureBuilder configureBuilder = Configure.builder();
        configureBuilder.useSpringEL();
        configureBuilder.bind("mymd", new MarkdownRenderPolicy());

        // compile 编译模板
        // render 渲染数据
        // write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same, configureBuilder.build()).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
