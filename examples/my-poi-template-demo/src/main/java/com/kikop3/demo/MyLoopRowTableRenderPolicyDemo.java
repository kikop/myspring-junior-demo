package com.kikop3.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.kikop3.demo.model.MyUserInfo;
import com.kikop3.demo.model.MyUserInfoWrap;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * feature:循环复制渲染表格的某一行
 */
public class MyLoopRowTableRenderPolicyDemo {

    /**
     * 构建测试数据
     *
     * @return
     * @throws IOException
     */
    private static List<MyUserInfo> buildSectionBlock() throws IOException {

        // 设置图片标签:
        // 右击已有图片:设计图片->文字->标题,输入标题 picture,可能是个内置bug,但不影响使用
        List<MyUserInfo> sections = new ArrayList<>();
        // 25行
        for (int i = 0; i < 25; i++) {
            MyUserInfo item1 = new MyUserInfo(String.format("张三%d", i + 1), "13800138000", "北京市朝阳区");
            sections.add(item1);
        }
        // 26行
        MyUserInfo item1 = new MyUserInfo("审批人", "13800138000", "张大雷");
        sections.add(item1);
        return sections;
    }

    /**
     * 测试
     *
     * @throws IOException
     */
    public static void testRender() throws IOException {

        // 1.输出定义
        String srcClassPath_same = "template/mypoitl13_looprowtable.docx";
        String srcClassPath_notsame = "template/mypoitl13_looprowtable_notSameRow.docx";

        String destPath = "./result/dest_mypoitl13_looprowtable.docx";
        boolean isSameLne = false;

        // 2. 组装模板数据（key 对应模板中的 [[myUserInfoList.xxx]]）
        MyUserInfoWrap itemWrap = new MyUserInfoWrap();
        List<MyUserInfo> itemsList = buildSectionBlock();
        itemWrap.setMyUserInfoList(itemsList);

        // 3. 渲染模板（模板路径自己修改）
        ClassPathResource tpl = new ClassPathResource(isSameLne ? srcClassPath_same : srcClassPath_notsame);

        // 默认前缀:[,后缀:]
        // onSameLine,true:使用当前行的占位符
//        LoopRowTableRenderPolicy hackLoopSameLineTableRenderPolicy = new LoopRowTableRenderPolicy(true);
        LoopRowTableRenderPolicy hackLoopSameLineTableRenderPolicy = new LoopRowTableRenderPolicy(isSameLne);
        Configure config = Configure.builder()
                // 实体类要有字段,itemWrap.items
                .bind("myUserInfoList", hackLoopSameLineTableRenderPolicy).build();
        XWPFTemplate template = XWPFTemplate.compile(tpl.getInputStream(), config)
                .render(itemWrap);
        // 4.关闭流
        template.writeToFile(destPath);
    }
}
