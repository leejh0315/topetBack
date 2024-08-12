package topetBack.topetBack.report.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.report.dao.ReportRepository;
import topetBack.topetBack.report.domain.CommentReportEntity;
import topetBack.topetBack.report.domain.CommunityReportEntity;
import topetBack.topetBack.report.domain.ReportEntitiy;

import java.util.Optional;

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
                        .community(community)
                        .build();
                
                reportRepository.save(reportEntity);
                CommunityReportEntity dto = reportEntity.toCommunityReportDTO();
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404).body("커뮤니티를 찾을 수 없습니다.");
            }
        } else if ("comment".equals(type)) {
            Optional<CommentEntity> commentOpt = commentRepository.findById(id);
            if (commentOpt.isPresent()) {
                CommentEntity comment = commentOpt.get();
                ReportEntitiy reportEntity = ReportEntitiy.builder()
                        .author(author)
                        .reason(reason)
                        .comment(comment)
                        .build();
                
                reportRepository.save(reportEntity);
                CommentReportEntity dto = reportEntity.toCommentReportDTO();
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404).body("댓글을 찾을 수 없습니다.");
            }
        } else {
            return ResponseEntity.status(400).body("잘못된 신고 유형입니다.");
        }
    }
}
