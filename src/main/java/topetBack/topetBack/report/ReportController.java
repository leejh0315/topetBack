package topetBack.topetBack.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.SessionMember;
import topetBack.topetBack.report.application.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
	
	public final ReportService reportService;
    private final SessionManager sessionManager;

	@PostMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> reportPost(@PathVariable("id") Long id, HttpServletRequest req , @RequestBody String reason) throws JsonMappingException, JsonProcessingException {
		SessionMember member = sessionManager.getSessionObject(req);
		
        return reportService.reportPost(id, member.toMember() , reason);
    }
}
