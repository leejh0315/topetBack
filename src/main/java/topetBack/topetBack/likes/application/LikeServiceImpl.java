package topetBack.topetBack.likes.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.likes.dao.LikeRepository;
import topetBack.topetBack.likes.domain.LikeResponseDTO;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl  implements LikeService{
	
	private final LikeRepository likeRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<CommunityResponseDTO> likePost(Long id , Long memberId) {
    	
    	Optional<Member> member = memberRepository.findById(memberId);
    	
    	
    	Optional<CommunityEntity> community = communityRepository.findById(id);
    	if(community.isEmpty()) {
    		
    	}
    	
    	Optional<Likes> found = likeRepository.findByCommunityAndAuthor(community.get(), member.get());
    	if(found.isEmpty()) { //좋아요 X
    		Likes likes = Likes.of(community.get(), member.get());
    		likeRepository.save(likes);
    	} else {
    		likeRepository.delete(found.get());
    		likeRepository.flush();
    	}
    		
    	
        return ResponseEntity.ok(community.get().toResponseDTO());
    }

	@Override
	public List<LikeResponseDTO> findByAuthor(Member member) {
		List<Likes> likes = likeRepository.findByAuthor(member);
		return likes.stream()
                .map(Likes::toResponseDTO)
                .collect(Collectors.toList());
	}
	
	
	 @Transactional
	 public boolean getDetailLike(Long id , Member member) {
		 List<Likes> likes = likeRepository.findByAuthor(member);
		 
		 Optional<CommunityEntity> community = communityRepository.findById(id);
		 if(community.isEmpty()) {
			 System.out.println("해당 게시물이 없습니다");
		 }
		 
		 boolean likedByUser = likeRepository.existsByCommunityAndAuthor(community.get(), member);
		 return likedByUser;
		 
	 }
	

}
