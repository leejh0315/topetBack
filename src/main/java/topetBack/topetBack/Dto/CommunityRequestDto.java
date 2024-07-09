package topetBack.topetBack.Dto;

import lombok.Getter;
import topetBack.topetBack.form.CommunityPostForm;

@Getter
public class CommunityRequestDto {
		private Long id;
	    private String title; // 제목
	    private String content; // 내용

	    public CommunityPostForm toEntity() {
	        return CommunityPostForm.builder()
	        		.id(id)
	                .title(title)
	                .content(content)
	                .build();
	    }

}
