package topetBack.topetBack.domain;

import java.awt.Image;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "community")
@ToString
@Entity
@MappedSuperclass
public class Community {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Column(name = "post_id")
	private Long id;
	
//	 @ManyToOne(fetch = FetchType.LAZY
//	 @JoinColumn(name = "member_id")
//	 private Member member;
	 
	@CreationTimestamp
    private LocalDateTime createdTime;
	
	@UpdateTimestamp
    private LocalDateTime updatedTime;
	
	@ColumnDefault("true")
	private Boolean deleted;
	
    @Column(nullable = false)
	private String title;
    
    @Column(nullable = false)
    @Lob
    private String contents;
    
    @Column(nullable = false)
    private String hashtag;
    
//    @OneToMany(mappedBy = "boadr", cashcade = CascadeType.PERSIST , orphanRemoval = true)
//    private List<Image> images;
    //프로필 , 스케줄 , 커뮤니티 , 쇼츠
    
   
}
