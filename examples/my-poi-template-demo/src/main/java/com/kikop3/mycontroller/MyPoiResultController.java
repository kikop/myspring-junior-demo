package com.kikop3.mycontroller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import com.kikop3.demo.MyQuickDataModelDemo;
import com.kikop3.utils.PoiTlToPdfUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;


//防止走视图解析器
@RestController
@RequestMapping("myrc")
public class MyPoiResultController {


    // http://localhost:8383/mypoi/myrc/testForDownload
    @GetMapping("testForDownload")
    public void testForDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        XWPFTemplate xwpfTemplate = MyQuickDataModelDemo.testReturnRender();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=\"" + "out_template.docx" + "\"");


        OutputStream out = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        xwpfTemplate.write(bos);
        bos.flush();
        out.flush();
        PoitlIOUtils.closeQuietlyMulti(xwpfTemplate, bos, out);
    }


    // http://localhost:8383/mypoi/myrc/testForPreview2
    @GetMapping("testForPreview2")
    public void testForPreview2(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1.构建 XWPFTemplate
        XWPFTemplate xwpfTemplate = MyQuickDataModelDemo.testReturnRender();

        // 2. docx -> pdf
        byte[] pdfBytes = PoiTlToPdfUtil.renderDocxToPdfBytes2(xwpfTemplate);

        // 3. 响应为 PDF（浏览器原生支持预览）
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");

        String fileName = "dest_out_template.docx";
        String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "inline; filename*=UTF-8''" + encodedFileName);
        response.setContentLength(pdfBytes.length);

        // 3. 可选：防止缓存（开发阶段推荐）
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (OutputStream out = response.getOutputStream()) {
            out.write(pdfBytes);
            out.flush();
        } finally {
            PoitlIOUtils.closeQuietlyMulti(xwpfTemplate);
        }
    }

    //    // http://localhost:8383/mypoi/myrc/testForPreview
//    @GetMapping("testForPreview")
//    public void testForPreview(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        XWPFTemplate xwpfTemplate = MyQuickDataModelDemo.testReturnRender();
//
//
//        // 1. Word(.docx) 正确的 MIME 类型
//        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//
//        // 2. inline = 浏览器内预览（关键）
//        String fileName = "dest_out_template.docx";
//        String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
//        response.setHeader("Content-Disposition", "inline; filename*=UTF-8''" + encodedFileName);
//
//        // 3. 可选：防止缓存（开发阶段推荐）
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        response.setHeader("Pragma", "no-cache");
//        response.setDateHeader("Expires", 0);
//
//        // 4.输出流
//        OutputStream out = response.getOutputStream();
//        BufferedOutputStream bos = new BufferedOutputStream(out);
//        xwpfTemplate.write(bos);
//        bos.flush();
//        out.flush();
//        PoitlIOUtils.closeQuietlyMulti(xwpfTemplate, bos, out);
//    }
}
