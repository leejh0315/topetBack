package topetBack.topetBack.community.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityRequestDTO {
	private String title;
    private String content;
	private Member author;
    private String hashtag;
    private String category;
    private String animal;
    private List<MultipartFile> images;


	public CommunityEntity toCommunityEntity() {

		FileGroupEntity fileGroupEntity = new FileGroupEntity();

		return CommunityEntity.builder()
				.author(this.author)
				.title(this.title)
				.content(this.content)
				.hashtag(this.hashtag)
				.category(this.category)
				.animal(this.animal)
				.fileGroupEntity(fileGroupEntity)
				.build();
	}

}
