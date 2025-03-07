package topetBack.topetBack.report.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Service
public interface ReportService {
    ResponseEntity<?> reportPost(String type, Long id, Long authorId, String reason);
}
