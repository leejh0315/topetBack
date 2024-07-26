package topetBack.topetBack.like.domain;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;



@Data
@Builder
public class LikeResponseDTO {
	private Long id;
	private Member author;
}
