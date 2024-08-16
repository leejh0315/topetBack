package topetBack.topetBack.hashTag.domain;

import java.util.List;

import org.hibernate.annotations.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import topetBack.topetBack.file.domain.FileGroupEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hashtag")
@ToString
@Entity
public class HashTagEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Comment("해시태그 번호")
    private Long id;
	
	private String tag;
	
    @ManyToOne
    @JoinColumn(name = "tag_mapping_id")
    private TagMapping tagMapping;
    
    public HashTagResponseDTO listToString() {
    	return HashTagResponseDTO.builder()
    			.tag(this.tag).build();
    
    }
}
