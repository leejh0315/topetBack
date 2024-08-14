package topetBack.topetBack.comment.application;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import topetBack.topetBack.block.dao.BlockRepository;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.comment.domain.MyCommentResponseDTO;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;


@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository commentRepository;
	private final CommunityRepository communityRepository;
	private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;



	 public CommentServiceImpl(CommentRepository commentRepository, CommunityRepository communityRepository, MemberRepository memberRepository , BlockRepository blockRepository) {
	        this.commentRepository = commentRepository;
	        this.communityRepository = communityRepository;
	        this.memberRepository = memberRepository;
	        this.blockRepository = blockRepository;

	    }

	 @Transactional
	 public CommentResponseDTO insert(Long CommunityId, CommentRequestDTO commentRequestDTO) throws NotFoundException, AccessDeniedException {
	     Member member = memberRepository.findById(commentRequestDTO.getAuthor().getId())
	             .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getAuthor().getId()));

	     CommunityEntity communityEntity = communityRepository.findById(CommunityId)
	             .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + CommunityId));
	     
	    
	     
	     CommentEntity commentEntity = commentRequestDTO.toCommentEntity();

	     if (commentRequestDTO.getParentId() != null) {
	         CommentEntity parentComment = commentRepository.findById(commentRequestDTO.getParentId())
	                 .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getParentId()));
	         commentEntity.updateParent(parentComment);
	     }
	    
	     commentEntity.updateCommunity(communityEntity);
	   

	     commentEntity.updateAuthor(member);

	     CommentEntity result = commentRepository.save(commentEntity);

	     return result.toResponseDTO();
	 }

	public List<CommentResponseDTO> getCommentsByCommunityId(Long communityId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Slice<CommentEntity> comments = commentRepository.findByCommunityId(communityId, pageable);
		return comments.stream().map(CommentEntity::toResponseDTO).collect(Collectors.toList());
	 }

	public List<MyCommentResponseDTO> getCommentsByAuthorId(Long authorId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Slice<CommentEntity> commentResponseDTOSlice = commentRepository.findByAuthorId(authorId, pageable);
		return commentResponseDTOSlice.stream().map(CommentEntity::toMyCommentResponseDTO).collect(Collectors.toList());
	}

	@Transactional
	public CommentResponseDTO updateComment(CommentRequestDTO commentRequestDTO) throws NotFoundException {
		CommentEntity comment = commentRepository.findById(commentRequestDTO.getId())
				.orElseThrow(() -> new NotFoundException("해당 댓글을 찾을수 없습니다 : " + commentRequestDTO.getId()));
		comment.setContent(commentRequestDTO.getContent());
		commentRepository.save(comment); // JPA save 메서드를 사용하여 업데이트
		return comment.toResponseDTO();
	}
	 
	 
	 @Transactional
	 public void delete(Long id) {
		 CommentEntity comment = commentRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을수 없습니다 : " + id));

		 commentRepository.delete(comment);
	 }

}
