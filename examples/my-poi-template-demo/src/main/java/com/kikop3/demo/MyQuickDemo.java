package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * feature:poi-tl快速入门
 */
public class MyQuickDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "mytemplate/myquickdemo.docx";
        //  默认当前工作目录相对路径
        String destPath = "myresult/dest_myquickdemo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        // compile 编译模板
        // render 渲染数据
        // write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(new HashMap<String, Object>() {{
            put("title", "Hi, poi-tl Word模板引擎");
//     put("baidu", new HyperlinkTextRenderData("https://www.baidu.com", "https://www.baidu.com"));
            put("baidu", new HyperlinkTextRenderData("baidu", "http://deepoove.com"));
        }});

        template.writeAndClose(new FileOutputStream(destPath));
    }
}
