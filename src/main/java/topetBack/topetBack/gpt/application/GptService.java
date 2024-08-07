package topetBack.topetBack.gpt.application;

import org.springframework.stereotype.Service;

@Service
public interface GptService {
	 public String getChatGPTResponse(String prompt);
}
