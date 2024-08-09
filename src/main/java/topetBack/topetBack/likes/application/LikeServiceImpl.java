package topetBack.topetBack.like.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.application.CommunityServiceImpl;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.like.dao.LikeRepository;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.like.domain.LikeResponseDTO;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.schedule.domain.ScheduleEntity;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl  implements LikeService{
	
	private final LikeRepository likeRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public ResponseEntity<CommunityResponseDTO> likePost(Long id , Member author) {
    	Optional<CommunityEntity> community = communityRepository.findById(id);
    	if(community.isEmpty()) {
    		
    	}
    	
    	Optional<Like> found = likeRepository.findByCommunityAndAuthor(community.get(), author);
    	if(found.isEmpty()) { //좋아요 X
    		Like like = Like.of(community.get(), author);
    		likeRepository.save(like);
    	} else {
    		likeRepository.delete(found.get());
    		likeRepository.flush();
    	}
    		
    	
        return ResponseEntity.ok(community.get().toResponseDTO());
    }

	@Override
	public List<LikeResponseDTO> findByAuthor(Member member) {
		List<Like> likes = likeRepository.findByAuthor(member);
		return likes.stream()
                .map(Like::toResponseDTO)
                .collect(Collectors.toList());
	}
	
	
	 @Transactional
	 public boolean getDetailLike(Long id , Member member) {
		 List<Like> like = likeRepository.findByAuthor(member);
		 
		 Optional<CommunityEntity> community = communityRepository.findById(id);
		 if(community.isEmpty()) {
			 System.out.println("해당 게시물이 없습니다");
		 }
		 
		 boolean likedByUser = likeRepository.existsByCommunityAndAuthor(community.get(), member);
		 return likedByUser;
		 
	 }
	

}
