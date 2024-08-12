package topetBack.topetBack.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
	private Long id;
	private String socialId;
	private String name;
	private String email;
	private String profileSrc;
}
