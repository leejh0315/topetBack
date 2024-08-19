package topetBack.topetBack.shorts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortsSummaryResponseDTO {
	private Long id;
    private String content;
    private String thumbnail;
    private int commentCount;
    private int likeCount;
    
}
