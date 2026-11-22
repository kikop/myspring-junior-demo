package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.jsonmodel.support.GsonPreRenderDataCastor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feature:列表
 */
public class MyJsonModelSupportDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "mytemplate/myjsonmodelsupportdemo.docx";
        //  默认当前工作目录相对路径
        String destPath = "myresult/dest_myjsonmodelsupportdemo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }


        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("title", Texts.of("Hello").color("FF0000").create());
//        data.put("img", Pictures.ofLocal("mytemplate/mylogo.png").size(100, 100).create());

        data.put("title", "Hello World");
        data.put("img", "mytemplate/mylogo.png");

        // compile 编译模板
        // render 渲染数据
        // write 输出到流
        ConfigureBuilder configureBuilder = Configure.builder();
        configureBuilder.addPreRenderDataCastor(new GsonPreRenderDataCastor());

        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same, configureBuilder.build()).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
