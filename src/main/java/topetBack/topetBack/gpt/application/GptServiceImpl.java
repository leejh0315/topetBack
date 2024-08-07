package topetBack.topetBack.gpt.application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class GptServiceImpl implements GptService{
	@Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String getChatGPTResponse(String prompt) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        // 시스템 메시지 규칙들을 변수로 저장
        String systemRules = "다음의 규칙을 따라야 합니다: " +
                "1. 동물 관련 질문이 아니면 '저는 동물에 관련된 질문만 할 수 있어요!'라고 답해줘. " +
                "2. 한국어가 아닌 다른 언어로 물어보면, '저는 한국어로만 답할 수 있어요!'라고 말한 후에 답변해줘. " +
                "3. 인사는 받아줘. " +
                "4. 어떤 언어가 가능하냐고 물어보면 한국어만 가능하다고 답해줘." +
                "5. 너의 이름을 물어보면 투펫AI라고 답해줘.";

        String json = String.format("{\n  \"model\": \"gpt-4o-mini\",\n  \"messages\": [\n    {\"role\": \"system\", \"content\": \"%s\"},\n    {\"role\": \"user\", \"content\": \"%s\"}\n  ],\n  \"temperature\": 0.8,\n  \"max_tokens\": 1024,\n  \"top_p\": 1,\n  \"frequency_penalty\": 0.5,\n  \"presence_penalty\": 0.5\n}", systemRules, prompt);
        RequestBody body = RequestBody.create(json, mediaType);

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String content = jsonNode.path("choices").get(0).path("message").path("content").asText();

            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
	
}
