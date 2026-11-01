package myspring.ai.demo.service;

import jakarta.annotation.Resource;
import myspring.ai.demo.model.DeepSeekMetaInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基于 基于HttpClient
 */
@Service
public class DeepSeekByChatClientService {

    @Resource
    private OpenAiChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private DeepSeekMetaInfo deepSeekMetaInfo;

    /**
     * OK1
     */
    public void runDeepSeek1ByOpenAiChatModel() {
        // 该类使用 OpenAiChatModel 接口，更适合专注于 OpenAI 的项目。
        OpenAiChatOptions openAiChatOptions = new OpenAiChatOptions();
        openAiChatOptions.setModel(deepSeekMetaInfo.getModel());

        OpenAiApi openAiApi = OpenAiApi.builder().apiKey(deepSeekMetaInfo.getApi_key()).baseUrl(deepSeekMetaInfo.getBase_url()).build();

        OpenAiChatModel model = OpenAiChatModel.builder().defaultOptions(openAiChatOptions).openAiApi(openAiApi).build();
    }


    /**
     * OK2
     */
    public void runDeepSeek2ByOpenAiChatModel() {
        String message = "你好，你是谁？";
        String result = chatModel.call(message);
        System.out.println(result);
    }

    /**
     * OK3
     */
    public void runByChatClient() {
        String response = chatClient.prompt("Tell me a joke").call().content();
        System.out.println(response);
    }


}
