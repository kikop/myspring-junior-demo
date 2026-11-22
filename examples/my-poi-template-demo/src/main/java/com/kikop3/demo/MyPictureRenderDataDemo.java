package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Texts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * feature:图片
 */
public class MyPictureRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        String srcClassPath_same = "template/mypicturerenderdatademo.docx";
        //  默认当前工作目录相对路径
        String destPath = "result/dest_mypicturerenderdatademo.docx";

        File file = new File(srcClassPath_same);
        if (!file.exists()) {
            // 模板文件不存在: E:\workdirectory\Dev\remotestudy2\myspring-junior-demo\template\myquickdemo1.docx
            throw new RuntimeException("模板文件不存在: " + file.getAbsolutePath());
        }

        Map<String, Object> data = new HashMap<String, Object>() {{
            put("title", Texts.of("kikop").create());

            // 指定图片路径
            put("Img", "mylogo.png");
            // svg图片
            put("svgImg", "https://img.shields.io/badge/jdk-1.6%2B-orange.svg");

            // 图片文件
            put("localImg", Pictures.ofLocal("mylogo.png").size(120, 120).create());

            // 图片流
            put("streamImg", Pictures.ofStream(new FileInputStream("mylogo.png"), PictureType.JPEG).size(100, 120).create());

            // 网络图片(注意网络耗时对系统可能的性能影响)
            put("urlImg", Pictures.ofUrl("http://deepoove.com/images/icecream.png").size(100, 100).create());

            // java图片，我们可以利用Java生成图表插入到word文档中
//            File file = new File("D:/images/test.png");
//            BufferedImage bufferImage = ImageIO.read(file);

            try(InputStream is = new FileInputStream("mylogo.png")){
                BufferedImage bufferImage = ImageIO.read(is);
                if (bufferImage == null) {
                    throw new RuntimeException("图片读取失败");
                }
                put("bufferedImg", Pictures.ofBufferedImage(bufferImage, PictureType.PNG).size(100, 100).create());
            }

        }};


        //        compile 编译模板
        //        render 渲染数据
        //        write 输出到流
        XWPFTemplate template = XWPFTemplate.compile(srcClassPath_same).render(data);

        template.writeAndClose(new FileOutputStream(destPath));
    }

}
