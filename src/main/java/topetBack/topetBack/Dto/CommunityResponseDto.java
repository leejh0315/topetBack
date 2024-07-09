package topetBack.topetBack.Dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import topetBack.topetBack.form.CommunityPostForm;

@Getter
public class CommunityResponseDto {
    private Long id;	
    private LocalDateTime createdTime;	
    private LocalDateTime updatedTime;	
	private String title;
    private String content;    
    private String hashtag;   
    private String category;
    private String animal;
    
    public CommunityResponseDto(CommunityPostForm entity) {
    	this.id = entity.getId();
    	this.createdTime = entity.getCreatedTime();
    	this.updatedTime = entity.getUpdatedTime();
    	this.title = entity.getTitle(); 
    	this.content = entity.getContent();
    	this.hashtag = entity.getHashtag();
    	this.category = entity.getCategory();
    	this.animal = entity.getAnimal();
    	
    }
	
}
