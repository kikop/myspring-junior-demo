package myspring.ai.demo.service;

import myspring.ai.demo.model.DeepSeekMetaInfo;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 基于 基于HttpClient
 */
@Service
public class DeepSeekByOkHttpClient {

    @Autowired
    private DeepSeekMetaInfo deepSeekMetaInfo;

    public void testChatCompletion() {
        String content = "{\n  \"messages\": [\n    {\n      \"content\": \"You are a helpful assistant\",\n      \"role\": \"system\"\n    },\n    {\n      \"content\": \"Hi\",\n      \"role\": \"user\"\n    }\n  ],\n  \"model\": \"deepseek-chat\",\n  \"frequency_penalty\": 0,\n  \"max_tokens\": 2048,\n  \"presence_penalty\": 0,\n  \"response_format\": {\n    \"type\": \"text\"\n  },\n  \"stop\": null,\n  \"stream\": false,\n  \"stream_options\": null,\n  \"temperature\": 1,\n  \"top_p\": 1,\n  \"tools\": null,\n  \"tool_choice\": \"none\",\n  \"logprobs\": false,\n  \"top_logprobs\": null\n}";
        // JSONObject parse = (JSONObject) JSONObject.parse(content);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://api.deepseek.com/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").addHeader("Authorization", String.format("Bearer %s", deepSeekMetaInfo.getApi_key())).build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
            int code = response.code();
            if (200 == code) {
                String message = response.message();
                ResponseBody body1 = response.body();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
