package topetBack.topetBack.gpt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import topetBack.topetBack.gpt.application.GptService;

@RestController
@RequestMapping("/chatgpt")
public class GptController {
	
	@Autowired
    private GptService gptService;

    @PostMapping("/ask")
    public Map<String, String> askChatGPT(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String response = gptService.getChatGPTResponse(prompt);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("response", response);
        return responseBody;
    }
}
