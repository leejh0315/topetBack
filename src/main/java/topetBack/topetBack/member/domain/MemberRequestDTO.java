package topetBack.topetBack.member.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
	private Long id;
	private String name;
	private MultipartFile photo;
	private String profileSrc;
	
	public Member toMember() {
		return Member.builder()
				.id(this.id)
				.build();
	}
}
