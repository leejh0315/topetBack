package topetBack.topetBack.community.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.Member;

@Data
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
