package com.kikop3.demo1;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.kikop3.demo1.model.MyUserInfo;
import com.kikop3.demo1.model.MyUserInfoWrap;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基本模板实现表格行循环功能1
 */
public class LoopRowTableRenderPolicyDemo {


    private static List<MyUserInfo> buildSectionBlock() throws IOException {

        // 设置图片标签:
        // 右击已有图片:设计图片->文字->标题,输入标题 picture,可能是个内置bug,但不影响使用
        List<MyUserInfo> sections = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            MyUserInfo item1 = new MyUserInfo(String.format("张三%d",i+1), "13800138000", "北京市朝阳区");
            sections.add(item1);
        }
        MyUserInfo item1 = new MyUserInfo("审批人", "13800138000", "张大雷");
        sections.add(item1);
        return sections;
    }

    public static void testRender() throws IOException {

        // 2. 组装模板数据（key 对应模板中的 [[item.xxx]]）
        MyUserInfoWrap itemWrap = new MyUserInfoWrap();
        List<MyUserInfo> itemsList = buildSectionBlock();
        itemWrap.setMyUserInfoList(itemsList);

        // 3. 渲染模板（模板路径自己修改）
        ClassPathResource tpl = new ClassPathResource("template/mypoitl13_looprowtable.docx");

        LoopRowTableRenderPolicy hackLoopSameLineTableRenderPolicy = new LoopRowTableRenderPolicy(true);
        Configure config = Configure.builder()
                // 实体类要有字段,itemWrap.items
                .bind("myUserInfoList", hackLoopSameLineTableRenderPolicy).build();
        XWPFTemplate template = XWPFTemplate.compile(tpl.getInputStream(), config).render(itemWrap);
        template.writeToFile("dest_mypoitl13_looprowtable.docx");
    }
}
