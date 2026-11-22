package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Texts;
import com.kikop3.demo.model.Author;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * feature:textRenderData
 */
public class MyTextRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/mytextrenderdatademo.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_mytextrenderdatademo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Author author = new Author("tom");


        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", Texts.of("kikop").create());
        data.put("author", Texts.of("Sayi").color("000000").create());
        data.put("link", Texts.of("website").link("http://deepoove.com").create());
        data.put("anchor", Texts.of("anchortxt\nabcd").anchor("appendix1").create());


        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
