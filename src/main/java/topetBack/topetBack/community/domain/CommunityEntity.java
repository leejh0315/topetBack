package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "community")
@ToString
@Entity
public class CommunityEntity {
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

    @Comment("작성자")
    @ManyToOne
    private Member author;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_group_id")
    private FileGroupEntity fileGroupEntity;


    public CommunityResponseDTO toResponseDTO() {

        List<FileResponseDTO> fileResponseDTOList = this.fileGroupEntity.getFileList()
                .stream().map(FileInfoEntity::toResponseDTO).collect(Collectors.toList());

        return CommunityResponseDTO.builder()
                .id(this.id)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .hashtag(this.hashtag)
                .category(this.category)
                .animal(this.animal)
                .images(this.fileGroupEntity.getFileResponseDTOList())
                .build();
    }

}
