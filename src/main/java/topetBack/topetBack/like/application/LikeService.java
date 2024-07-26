package topetBack.topetBack.like.application;

import java.util.List;

import org.springframework.http.ResponseEntity;

import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.like.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;

public interface LikeService {
	 public ResponseEntity<CommunityResponseDTO> likePost(Long id , Member author);
	 public List<LikeResponseDTO> findByAuthor(Member member);
}
