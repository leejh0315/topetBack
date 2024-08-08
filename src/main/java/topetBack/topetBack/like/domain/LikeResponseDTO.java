package topetBack.topetBack.like.domain;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;


@Data
@Builder
public class LikeResponseDTO {
	private Long id;
	private MemberSummaryResponseDTO author;
}
