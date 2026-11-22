package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Texts;
import com.kikop3.demo.model.AddrModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feature:tableRenderData
 */
public class MySpringExpLangDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/myspringeldatademo.docx";

        //  默认当前工作目录相对路径
        String destPath = "result/dest_myspringeldatademo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }


        List<AddrModel> subData = new ArrayList<>();
        subData.add(new AddrModel("Hangzhou,China", "qqqqqqqqqqqq"));
        subData.add(new AddrModel("Shanghai,China", "qqqqqqqqqqqq222"));


        Map<String, Object> data = new HashMap<String, Object>() {{
            put("title", Texts.of("kikop").create());
            put("name", "kikop"); // 注意:要放String

            put("mydesc", "");
            put("mysummary", "Find A Pet");
//            put("myproduces", List.of("application/xml", "application2/xml", "application3/xml"));
//            put("mysubdata", subData);
            put("myproduces", subData);
        }};
        ConfigureBuilder configureBuilder = Configure.builder();
        configureBuilder.useSpringEL();

        // compile 编译模板
        // render 渲染数据
        // write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same, configureBuilder.build()).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
