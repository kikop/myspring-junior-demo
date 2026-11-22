package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.DocumentRenderPolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * feature:动态修改模板并生成结果
 */
public class MyNoTemplateRenderDataDemo {


    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        //  默认当前工作目录相对路径
        //  或者绝对路径 D:/xxx/template/myquickdemo.docx
        //  默认当前工作目录相对路径
        String destPath = "myresult/dest_mynotemplaterenderdatademo.docx";

        // Texts 只暴露最常用的样式快捷方法，复杂样式走 Style Builder。
        String text = "this a paragraph";
        // 正确做法：要么全部走 Style Builder，要么全部走 Texts 快捷方法，不要混用。
        Style style = Style.builder().buildColor("FF0000").buildFontSize(14).buildStrike() // ✅ 在 Builder 里设
                .build();
        //TextRenderData textRenderData = Texts.of(text).style(style).color("FF0000").fontSize(14).create();
        TextRenderData textRenderData = Texts.of(text).style(style).create();

        ParagraphRenderData paragraphRenderData_text =//Paragraphs.of(text)
                Paragraphs.of(textRenderData) // 纯字符串 + 想改颜色（最简）
                        .center() // 纯字符串段落用 defaultTextStyle 基本无效，直接对 Paragraphs 或 Texts 设 style 才是正道。
                        .create();

        // FilePictureRenderData实例
        PictureRenderData pictureRenderData = Pictures.ofLocal("mytemplate/mylogo.png").create();
        ParagraphRenderData paragraphRenderData_picture = Paragraphs.of(pictureRenderData).right().create();
        DocumentRenderData documentRenderData = Documents.of().addParagraph(paragraphRenderData_text).addParagraph(paragraphRenderData_picture).create();
        XWPFTemplate template = XWPFTemplate.create(documentRenderData);

        template.writeAndClose(new FileOutputStream(destPath));


    }

}
