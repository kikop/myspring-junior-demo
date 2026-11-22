package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.kikop3.demo.model.Author;
import com.kikop3.demo.model.MyDataModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * feature:data-model输出
 */
public class MyQuickDataModelDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/myquickdatamodeldemo.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_myquickdatamodeldemo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Author author = new Author("tom");
        MyDataModel myDataModel = new MyDataModel("kikop", "2026-11-08", author);

        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(myDataModel);

        template.writeAndClose(new FileOutputStream(destPath));
    }

    /**
     * 测试
     *
     * @throws IOException
     */
    public static XWPFTemplate testReturnRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/myquickdatamodeldemo.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_myquickdatamodeldemo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Author author = new Author("tom");
        MyDataModel myDataModel = new MyDataModel("kikop2333", "2026-11-08", author);

        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(myDataModel);

//        template.writeAndClose(new FileOutputStream(destPath));
        return template;
    }
}
