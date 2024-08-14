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
import topetBack.topetBack.member.dao.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public ResponseEntity<?> reportPost(String type, Long id, Long authorId, String reason) {
    	Optional<Member> member = memberRepository.findById(authorId);
    	
        if ("community".equals(type)) {
            Optional<CommunityEntity> communityOpt = communityRepository.findById(id);
            if (communityOpt.isPresent()) {
                CommunityEntity community = communityOpt.get();
                ReportEntitiy reportEntity = ReportEntitiy.builder()
                        .author(member.get())
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
                        .author(member.get())
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
