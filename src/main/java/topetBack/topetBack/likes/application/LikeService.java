package topetBack.topetBack.likes.application;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.likes.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Service
public interface LikeService {
	 public ResponseEntity<?> likePost(String type, Long id, Long memberId);
	 public List<LikeResponseDTO> findByAuthor(Member member);
	 public boolean getDetailLike(Long id , Member member);
}
