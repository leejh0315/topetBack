package topetBack.topetBack.member.domain;

import jakarta.servlet.http.Cookie;
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

	Cookie cookie;
}
