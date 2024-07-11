package topetBack.topetBack.community.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.file.domain.Image;
import topetBack.topetBack.user.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class CommunityDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
	@Comment("게시판 번호")
	private Long id;
		
	@CreationTimestamp
	@Comment("업로드 시간")
	private LocalDateTime createdTime;
		
	@UpdateTimestamp
	@Comment("업데이트 시간")
    private LocalDateTime updatedTime;	
		
	@ManyToOne(cascade = CascadeType.MERGE , targetEntity = Member.class)
	@JoinColumn(name = "member_id" , updatable = false)
    private Member member;
		
		
    @Column(nullable = false)
	@Comment("제목") 
	private String title;
	    
     @Column(nullable = false)
	 @Lob
     @Comment("내용")
	 private String content;
	    
	 @Column(nullable = true)
     @Comment("해시태그")
	 private String hashtag;
	    
	 @Column(nullable = false)
     @Comment("카테고리")
	 private String category;
	    
	 @Column(nullable = true)
     @Comment("반려 동물")
	 private String animal;
	    
	 @OneToMany(
	     mappedBy = "communityDomain",
	     cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
	      orphanRemoval = true
   )
	 private List<Image> image = new ArrayList<>(); 
	    
	    
    @Builder
	public CommunityDomain(String title, String content, String hashtag , String category , String animal) {
	    this.title = title;
	    this.content = content;
	    this.hashtag = hashtag;
	    this.category = category;
	    this.animal = animal;
	}
    
    public CommunityDomain toEntity() {
    	return CommunityDomain.builder()
    			.member(member)
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
    
    public void addPhoto(Image image) {
        this.image.add(image);

	// 게시글에 파일이 저장되어있지 않은 경우
        if(image.getCommunityDomain() != this)
            // 파일 저장
        	image.setBoard(this);
    }
 
    
    
    
}
