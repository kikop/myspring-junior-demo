package com.kikop3.mycomponent;

import com.aspose.words.License;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Slf4j
public class RegisterAsposeLicense {

    @PostConstruct
    public void initAspose() throws Exception {

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("license/asposelicense.lic")) {
            if (is == null) {
                log.warn("未找到 Aspose license，PDF 会有 Evaluation 水印");
                return;
            }
            License license = new License();
            license.setLicense(is);

//     Document doc = new Document();
//     doc.getFirstSection().getBody().getFirstParagraph().getRuns().add(new com.aspose.words.Run(doc, "Test"));
//     doc.save("testinitAspose.pdf");
//     log.info("OK - 无水印");
        }
    }

}
