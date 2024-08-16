package topetBack.topetBack.hashTag.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tagMapping")
@ToString
@Entity
public class TagMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long id;

    @OneToMany(mappedBy = "tagMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTagEntity> hashTag = new ArrayList<>();
    
    public List<HashTagResponseDTO> getHashTagResponseDTOList() {
        return hashTag.stream().map(HashTagEntity::listToString).collect(Collectors.toList());
    }

}
