package topetBack.topetBack.report.application;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.report.dao.ReportRepository;
import topetBack.topetBack.report.domain.CommentReportEntity;
import topetBack.topetBack.report.domain.CommunityReportEntity;
import topetBack.topetBack.report.domain.ReportEntitiy;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public ResponseEntity<?> reportPost(String type, Long id, Member author, String reason) {
        if ("community".equals(type)) {
            Optional<CommunityEntity> communityOpt = communityRepository.findById(id);
            if (communityOpt.isPresent()) {
                CommunityEntity community = communityOpt.get();
                ReportEntitiy reportEntity = ReportEntitiy.builder()
                        .author(author)
                        .reason(reason)
                        .community(community) // community 설정
                        .build();
                
                reportRepository.save(reportEntity);
                return ResponseEntity.ok(reportEntity.toCommunityResponseDTO());
            } else {
                return ResponseEntity.status(404).body("커뮤니티 찾을수없습");
            }
        } else if ("comment".equals(type)) {
            Optional<CommentEntity> commentOpt = commentRepository.findById(id);
            if (commentOpt.isPresent()) {
                CommentEntity comment = commentOpt.get();
                ReportEntitiy reportEntity = ReportEntitiy.builder()
                        .author(author)
                        .reason(reason)
                        .comment(comment) // comment 설정
                        .build();
                
                reportRepository.save(reportEntity);
                return ResponseEntity.ok(reportEntity.toCommentResponseDTO());
            } else {
                return ResponseEntity.status(404).body("댓글 찾을수없습");
            }
        } else {
            return ResponseEntity.status(400).body("Invalid report type");
        }
    }
}