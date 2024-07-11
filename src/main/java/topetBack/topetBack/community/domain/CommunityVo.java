package topetBack.topetBack.community.domain;

import java.beans.ConstructorProperties;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
public class CommunityVo {
	private String title;
    private String content;
    private String hashtag;
    private String category;                       
    private String animal;    
    private List<MultipartFile> image;
    
    @Builder
    @ConstructorProperties({"title", "content", "hashtag", "category", "animal"})
	public CommunityVo(String title, String content, String hashtag , String category , String animal) {
	    this.title = title;
	    this.content = content;
	    this.hashtag = hashtag;
	    this.category = category;
	    this.animal = animal;
	}
}
