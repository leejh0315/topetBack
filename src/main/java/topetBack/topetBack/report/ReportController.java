package topetBack.topetBack.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.report.application.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
	
	public final ReportService reportService;
    private final SessionManager sessionManager;

	@PostMapping("/")
    public ResponseEntity<?> reportPost(@RequestParam(name = "type") String type,@RequestParam(name = "id") Long id, HttpServletRequest req , @RequestBody String reason) throws JsonMappingException, JsonProcessingException {
//		SessionMember member = sessionManager.getSessionObject(req);
		Long memberId = sessionManager.getSessionObject(req);
        return reportService.reportPost(type , id, memberId, reason);
    }
}
