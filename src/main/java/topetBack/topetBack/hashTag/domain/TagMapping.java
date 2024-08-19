package topetBack.topetBack.hashTag.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "tag_mapping")
public class TagMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long id;

    @OneToMany(mappedBy = "tagMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HashTagEntity> hashTag = new HashSet<>();
//    private List<HashTagEntity> hashTag = new ArrayList<>();
    
    public List<HashTagResponseDTO> getHashTagResponseDTOList() {
        return hashTag.stream().map(HashTagEntity::listToString).collect(Collectors.toList());
    }

}
