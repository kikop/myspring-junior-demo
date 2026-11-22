package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.kikop3.demo.model.AddrModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feature:模板包含子模板，子模板再包含子模板
 */
public class MyDocxRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/mydocxrenderdatademo.docx";
        String srcClassPath_same2 = "template/mysub.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_mydocxrenderdatademo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        File file2 = new File(srcClassPath_same2);
        if (!file2.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file2.getAbsolutePath());
        }


        List<AddrModel> subData = new ArrayList<>();
        subData.add(new AddrModel("Hangzhou,China","qqqqqqqqqqqq"));
        subData.add(new AddrModel("Shanghai,China","qqqqqqqqqqqq222"));

        Map<String, Object> data = new HashMap<String, Object>() {{
            put("title", Texts.of("kikop").create());
//            put("mylist", Numberings.create("Plug-in grammar",
//                    "Supports word text, pictures, table...",
//                    "Not just templates"));
            //  模版中设定每行的字段,注意路径
            put("nested", Includes.ofLocal("template/mysub.docx").setRenderModel(subData).create());
        }};


        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
