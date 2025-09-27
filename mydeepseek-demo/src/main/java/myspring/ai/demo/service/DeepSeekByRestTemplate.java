package myspring.ai.demo.service;

import myspring.ai.demo.model.DeepSeekMetaInfo;
import myspring.ai.demo.model.DeepSeekRequest;
import myspring.ai.demo.model.DeepSeekResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 基于 基于HttpClient
 */
@Service
public class DeepSeekByRestTemplate {

    private final RestTemplate restTemplate;

    @Autowired
    private DeepSeekMetaInfo deepSeekMetaInfo;


    public DeepSeekByRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getChatResponse(String userMessage) {

        // 构造请求体
        DeepSeekRequest request = new DeepSeekRequest(deepSeekMetaInfo.getModel(), List.of(new DeepSeekRequest.Message("user", userMessage)), 0.7, 1000);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(deepSeekMetaInfo.getApi_key());

        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        // 发送请求
        ResponseEntity<DeepSeekResponse> response = restTemplate.exchange(
                deepSeekMetaInfo.getBase_url(), HttpMethod.POST, entity, DeepSeekResponse.class);

        // 处理响应
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().choices().get(0).message().content();
        }
        throw new RuntimeException("API请求失败: " + response.getStatusCode());
    }


}
