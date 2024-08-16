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
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;
import topetBack.topetBack.likes.dao.LikeRepository;
import topetBack.topetBack.likes.domain.LikeCommunityDTO;
import topetBack.topetBack.likes.domain.LikeResponseDTO;
import topetBack.topetBack.likes.domain.LikeShortsDTO;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;
import topetBack.topetBack.shorts.dao.ShortsRepository;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl  implements LikeService{
	
	private final LikeRepository likeRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final ShortsRepository shortsRepository;

    @Transactional
    public ResponseEntity<?> likePost(String type, Long id, Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (memberOpt.isEmpty()) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }

        Member member = memberOpt.get();

        if ("community".equals(type)) {
            Optional<CommunityEntity> communityOpt = communityRepository.findById(id);
            if (communityOpt.isPresent()) {
                CommunityEntity community = communityOpt.get();

                Optional<Likes> foundLike = likeRepository.findByCommunityAndAuthor(community, member);
                if (foundLike.isEmpty()) {
                    Likes newLike = Likes.builder()
                        .community(community)
                        .author(member)	
                        .build();
                    likeRepository.save(newLike);

                    LikeCommunityDTO dto = newLike.toCommunityDTO();
                    return ResponseEntity.ok(dto);
                } else {
                    likeRepository.delete(foundLike.get());
                    return ResponseEntity.ok("좋아요가 취소되었습니다.");
                }
            } else {
                return ResponseEntity.status(404).body("커뮤니티를 찾을 수 없습니다.");
            }
        } else if ("shorts".equals(type)) {
            Optional<ShortsEntity> shortsOpt = shortsRepository.findById(id);
            if (shortsOpt.isPresent()) {
                ShortsEntity shorts = shortsOpt.get();

                Optional<Likes> foundLike = likeRepository.findByShortsAndAuthor(shorts, member);
                if (foundLike.isEmpty()) {
                    Likes newLike = Likes.builder()
                        .shorts(shorts)
                        .author(member)
                        .build();
                    likeRepository.save(newLike);

                    LikeShortsDTO dto = newLike.toShortsDTO();
                    return ResponseEntity.ok(dto);
                } else {
                    likeRepository.delete(foundLike.get());
                    return ResponseEntity.ok("좋아요가 취소되었습니다.");
                }
            } else {
                return ResponseEntity.status(404).body("쇼츠를 찾을 수 없습니다.");
            }
        } else {
            return ResponseEntity.status(400).body("잘못된 좋아요 유형입니다.");
        }
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
