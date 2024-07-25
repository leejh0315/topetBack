package topetBack.topetBack.like.application;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.like.dao.LikeRepository;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl {
	
	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;
	private final CommunityRepository communityRepository;
	
	@Transactional
	public ResponseEntity<CommunityResponseDTO> likePost(Long id , Member author){
		
		Optional<CommunityEntity> community = communityRepository.findById(id);
		if(community.isEmpty()) {
		}
		
		Optional<Like> likeFound = likeRepository.findByCommunityAndAuthor(community.get(), author);
		if(likeFound.isEmpty()) { //좋아요 누른적이 업을때
			
			
		}
		
		return null;
	}

}
