package topetBack.topetBack.gpt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.gpt.application.GptService;
import topetBack.topetBack.member.domain.Member;

@RestController
@RequestMapping("/chatgpt")
@RequiredArgsConstructor
public class GptController {
	
	@Autowired
    private GptService gptService;
	
	@Autowired
	private SessionManager sessionManager;

    @PostMapping("/ask")
    public Map<String, String> askChatGPT(@RequestBody Map<String, String> request , HttpServletRequest req) throws  JsonProcessingException {
    	Member sessionMember = sessionManager.getSessionObject(req).toMember();
        String prompt = request.get("prompt");
        String response = gptService.getChatGPTResponse(prompt);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("response", response);
        return responseBody;
    }
}
