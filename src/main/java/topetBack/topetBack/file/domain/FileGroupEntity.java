package topetBack.topetBack.file.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "file_group")
public class FileGroupEntity {

    @Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
    private Long id;

    @OneToMany(mappedBy = "fileGroupEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FileInfoEntity> fileList = new ArrayList<>();

    public List<FileResponseDTO> getFileResponseDTOList() {
        return fileList.stream().map(FileInfoEntity::toResponseDTO).collect(Collectors.toList());
    }

}