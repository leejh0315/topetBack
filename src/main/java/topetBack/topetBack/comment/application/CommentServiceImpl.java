package topetBack.topetBack.comment.application;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.dao.CommentRepositoryCustom;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;


@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository commentRepositoy;
	private final CommunityRepository communityRepositoy;
	private final MemberRepository memberRepositoty;
	private final CommentRepositoryCustom commentRepositoryCustom;

	 public CommentServiceImpl(CommentRepository commentRepositoy, CommunityRepository communityRepositoy , MemberRepository memberRepositoty , CommentRepositoryCustom commentRepositoryCustom) {
	        this.commentRepositoy = commentRepositoy;
	        this.communityRepositoy = communityRepositoy;
	        this.memberRepositoty = memberRepositoty;
	        this.commentRepositoryCustom = commentRepositoryCustom;

	    }

	 @Transactional
	 public CommentResponseDTO insert(Long CommunityId, CommentRequestDTO commentRequestDTO) throws NotFoundException {
	     Member member = memberRepositoty.findById(commentRequestDTO.getAuthor().getId())
	             .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getAuthor().getId()));

	     CommunityEntity communityEntity = communityRepositoy.findById(CommunityId)
	             .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + CommunityId));
	     
	     CommentEntity commentEntity = commentRequestDTO.toCommentEntity();

	     if (commentRequestDTO.getParentId() != null) {
	         CommentEntity parentComment = commentRepositoy.findById(commentRequestDTO.getParentId())
	                 .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getParentId()));
	         commentEntity.updateParent(parentComment);
	     }
	         commentEntity.updateCommunity(communityEntity);
	   

	     commentEntity.updateAuthor(member);

	     CommentEntity result = commentRepositoy.save(commentEntity);

	     return result.toResponseDTO();
	 }

	 
	@Transactional
	public List<CommentResponseDTO> getCommentsByCommunityId(Long communityId) {
		return commentRepositoryCustom.findByCommunityId(communityId);
	 }

	 @Transactional
	    public CommentResponseDTO updateComment(CommentRequestDTO commentRequestDTO) throws NotFoundException {
	        CommentEntity comment = commentRepositoy.findById(commentRequestDTO.getId())
	                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getId()));
	        comment.setContent(commentRequestDTO.getContent());
	        commentRepositoy.save(comment); // JPA save 메서드를 사용하여 업데이트
	        return comment.toResponseDTO();
	    }
	 
	 
	 @Transactional
	 public void delete(Long id) {
		 CommentEntity comment = commentRepositoy.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을수 없습니다 : " + id));
		 
		 commentRepositoy.delete(comment);
	 }

}
