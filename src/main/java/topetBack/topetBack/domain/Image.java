package topetBack.topetBack.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.form.CommunityPostForm;

@Data
@NoArgsConstructor
@Entity
public class Image {
	@Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
	private Long fileId;
	
    @Column(nullable = false)
	private String fileUrl;
    
    @Column(nullable = false)
	private String fileUUID;

	
	@Builder
    public Image(Long fileId, String fileUrl, String fileUUID) {
    	this.fileId = fileId;
    	this.fileUrl = fileUrl;
    	this.fileUUID = fileUUID;
    }
}


