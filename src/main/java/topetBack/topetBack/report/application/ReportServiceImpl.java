package topetBack.topetBack.report.application;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.report.dao.ReportRepository;
import topetBack.topetBack.report.domain.ReportEntitiy;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
	
	private final ReportRepository reportRepository;
	private final CommunityRepository communityRepository;
	
	 @Transactional
	 public ResponseEntity<CommunityResponseDTO> reportPost(Long id , Member author , String reason) {
	    Optional<CommunityEntity> community = communityRepository.findById(id);
	    if(community.isEmpty()) {
	    		
	    }
	    	
	    Optional<ReportEntitiy> found = reportRepository.findByCommunityAndAuthor(community.get(), author);
	    	ReportEntitiy report = ReportEntitiy.of(community.get(), author , reason);
	    	reportRepository.save(report);
	   
	    		
	    	
	        return ResponseEntity.ok(community.get().toResponseDTO());
	    }
}
