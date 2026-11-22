package com.kikop3.utils;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PoiTlToPdfUtil {

    /**
     * poi-tl 渲染后的 XWPFTemplate -> PDF 字节数组
     */
    public static byte[] renderDocxToPdfBytes(XWPFTemplate template) throws Exception {

        // 1. poi-tl 把 docx 写到内存
        ByteArrayOutputStream docxOut = new ByteArrayOutputStream();
        try {
            template.write(docxOut);
        } finally {
            // 确保 template 一定被关闭（即使 write 抛异常）
            PoitlIOUtils.closeQuietlyMulti(template, docxOut);
        }

        // 2. Aspose 加载 docx 字节流并转为pdf
        try (ByteArrayInputStream docxIn = new ByteArrayInputStream(docxOut.toByteArray()); ByteArrayOutputStream pdfOut = new ByteArrayOutputStream()) {
            Document doc = new Document(docxIn);

            // 可选：PDF 参数
            // PdfSaveOptions options = new PdfSaveOptions();
            // options.setFontEmbeddingMode(FontEmbeddingMode.EMBED_ALL);
            // doc.save(pdfOut, options);

            doc.save(pdfOut, SaveFormat.PDF);
            pdfOut.flush();
            return pdfOut.toByteArray();
        }
    }

    /**
     * 推荐
     * poi-tl 渲染后的 XWPFTemplate -> PDF 字节数组
     */
    public static byte[] renderDocxToPdfBytes2(XWPFTemplate template) throws Exception {

        ByteArrayOutputStream docxOut = new ByteArrayOutputStream();
        try {
            template.writeAndClose(docxOut);
            try (ByteArrayInputStream docxIn = new ByteArrayInputStream(docxOut.toByteArray()); ByteArrayOutputStream pdfOut = new ByteArrayOutputStream()) {
                // 水印去除
                new Document(docxIn).save(pdfOut, SaveFormat.PDF);
                return pdfOut.toByteArray();
            }
        } finally {
            PoitlIOUtils.closeQuietlyMulti(template, docxOut);
        }
    }
}
