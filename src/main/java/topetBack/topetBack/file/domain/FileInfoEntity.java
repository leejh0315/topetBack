package topetBack.topetBack.file.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
@Entity
@Setter
@Table(name = "file_info")
public class FileInfoEntity {
    @Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
    private Long id;

    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    @Column(nullable = false)
    private String newFileName;	//파일 새로운 이름

    private Long fileSize;
    
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_group_id")
    private FileGroupEntity fileGroupEntity;


    public FileResponseDTO toResponseDTO() {

        return FileResponseDTO.builder()
        		.id(this.id)
                .path(this.filePath)
                .build();
    }


}