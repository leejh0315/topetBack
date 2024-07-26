package topetBack.topetBack.like.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;


@Data
@Builder
public class LikeResponseDTO {
	private Long id;
	private Member author;
	private CommunityEntity community;
}
