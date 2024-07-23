package topetBack.topetBack.comment.application.copy;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;


@Service
public class ComentServiceImpl implements ComentService{

	private final CommentRepository commentRepositoy;
	private final CommunityRepository communityRepositoy;
	private final MemberRepository memberRepositoty;

	 public ComentServiceImpl(CommentRepository commentRepositoy, CommunityRepository communityRepositoy , MemberRepository memberRepositoty) {
	        this.commentRepositoy = commentRepositoy;
	        this.communityRepositoy = communityRepositoy;
	        this.memberRepositoty = memberRepositoty;

	    }

	 @Override
	@Transactional
	 public CommentResponseDTO insert(Long CommunityId, CommentRequestDTO commentRequestDTO) throws NotFoundException {
	     Member member = memberRepositoty.findById(commentRequestDTO.getAuthor().getId())
	             .orElseThrow(() -> new NotFoundException("Could not found member id : " + commentRequestDTO.getAuthor().getId()));

	     CommunityEntity communityEntity = communityRepositoy.findById(CommunityId)
	             .orElseThrow(() -> new NotFoundException("Could not found community id : " + CommunityId));

	     CommentEntity commentEntity = commentRequestDTO.toCommentEntity();

	     if (commentRequestDTO.getParentId() != null) {
	         CommentEntity parentComment = commentRepositoy.findById(commentRequestDTO.getParentId())
	                 .orElseThrow(() -> new NotFoundException("Could not found comment id : " + commentRequestDTO.getParentId()));
	         commentEntity.updateParent(parentComment);
	     }

	     commentEntity.updateAuthor(member);
	     commentEntity.updateCommunity(communityEntity);

	     CommentEntity result = commentRepositoy.save(commentEntity);

	     return result.toResponseDTO();
	 }






}
