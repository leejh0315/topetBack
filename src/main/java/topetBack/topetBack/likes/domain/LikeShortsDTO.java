package topetBack.topetBack.likes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;
import topetBack.topetBack.report.domain.CommunityReportEntity;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LikeShortsDTO {
	private Long id;
	private MemberSummaryResponseDTO author;
    private ShortsSummaryResponseDTO shorts;

	

}
