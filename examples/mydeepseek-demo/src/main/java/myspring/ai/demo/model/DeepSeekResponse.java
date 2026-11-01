package myspring.ai.demo.model;

import java.util.List;

public record DeepSeekResponse(
        String id,
        String object,
        long created,
        String model,
        List<Choice> choices
) {
    public record Choice(
            int index,
            Message message,
            String finish_reason
    ) {
        public record Message(String role, String content) {
        }
    }
}
