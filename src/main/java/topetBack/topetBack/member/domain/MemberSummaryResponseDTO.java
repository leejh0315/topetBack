package topetBack.topetBack.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSummaryResponseDTO {
	private Long id;
	private String name;
	private String src;
}
