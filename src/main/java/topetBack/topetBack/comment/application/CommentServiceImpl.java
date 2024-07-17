package topetBack.topetBack.comment.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;


@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepositoy;
	private final CommunityRepository communityRepositoy;
	private final MemberRepository memberRepositoty;
	
	 public CommentServiceImpl(CommentRepository commentRepositoy, CommunityRepository communityRepositoy , MemberRepository memberRepositoty) { 
	        this.commentRepositoy = commentRepositoy;
	        this.communityRepositoy = communityRepositoy;
	        this.memberRepositoty = memberRepositoty;
	      
	    }
	
	@Transactional
	public Long commentSave(String name , Long id , CommentRequestDTO dto) {
		Member user = memberRepositoty.findByName(name);
		
		CommunityEntity communityEntity = communityRepositoy.findById(id).orElseThrow(() ->
				new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
		
		dto.setAuthor(user);
		dto.setCommunity(communityEntity);
		
		CommentEntity commentEntity = dto.toCommentEntity();
		commentRepositoy.save(commentEntity);
		
		return dto.getId();
	}
	

}
