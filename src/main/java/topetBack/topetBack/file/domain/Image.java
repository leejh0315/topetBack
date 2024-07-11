package topetBack.topetBack.file.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityDomain;

@Getter
@NoArgsConstructor
@Entity
public class Image {
	@Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
	private Long id;
	
	 @Column(nullable = false)
	 private String origFileName;  // 파일 원본명

	 @Column(nullable = false)
	 private String filePath;  // 파일 저장 경로

	 private Long fileSize;
	
    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityDomain communityDomain;
    
    @Builder
    public Image(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
	
 // Board 정보 저장
    public void setBoard(CommunityDomain communityDomain){
        this.communityDomain = communityDomain;
        
     // 게시글에 현재 파일이 존재하지 않는다면
        if(!communityDomain.getImage().contains(this))
            // 파일 추가
        	communityDomain.getImage().add(this);
    }

}