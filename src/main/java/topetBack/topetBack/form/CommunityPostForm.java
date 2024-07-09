package topetBack.topetBack.form;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.Image;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "community")
@ToString
@Entity
public class CommunityPostForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Column(name = "post_id")
	private Long id;
	
	@CreationTimestamp
    private LocalDateTime createdTime;
	
	@UpdateTimestamp
    private LocalDateTime updatedTime;	
	
//	@ColumnDefault("true")
//	private Boolean deleted;
	
    @Column(nullable = false)
	private String title;
    
    @Column(nullable = false)
    @Lob
    private String content;
    
    @Column(nullable = false)
    private String hashtag;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String animal;
    
    @Builder
    public CommunityPostForm(String title, String content, String hashtag , String category , String animal) {
    	this.title = title;
    	this.content = content;
    	this.hashtag = hashtag;
    	this.category = category;
    	this.animal = animal;
    }
    
    public static CommunityPostForm createPost(String title , String content , String hashtag, String category , String animal) {
    	return CommunityPostForm.builder()
    			.title(title)
    			.content(content)
    			.hashtag(hashtag)
    			.category(category)
    			.animal(animal)
    			.build();
    }
    
    public void update(String title, String content) {
    	this.title = title;
        this.content = content;
        this.updatedTime = LocalDateTime.now();
    }
    
}
