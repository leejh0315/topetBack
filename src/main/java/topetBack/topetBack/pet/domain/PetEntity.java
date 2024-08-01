package topetBack.topetBack.pet.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.member.domain.MemberResponseDTO;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
@ToString
@Entity
public class PetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
	@Comment("반려동물 번호")
	private Long id;

	@Comment("반려동물 타입")
	@Column(nullable = false)
	private String type;

	@Comment("반려동물 종")
	@Column(nullable = false)
	private String kind;

	@Column(nullable = false)
	@Comment("반려동물 성별")
	private String gender;
	
	@Column(nullable = true)
	@Comment("반려동물 중성화")
	private String neutered;

	@Column(nullable = false)
	@Comment("반려동물 이름")
	private String name;

	@Column(nullable = true)
	@Comment("생일")
	private String birth;

	@Column(nullable = true)
	@Comment("체중")
	private String weight;

	@Column(nullable = true)
	@Comment("알레르기")
	private String allergy;

	@Column(nullable = true)
	@Comment("건강상태")
	private String health;

	@Column(nullable = false, unique = true)
	@Comment("고유코드")
	private String uid;

	@Column(nullable = true)
	@Comment("사진경로")
	private String profileSrc;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "file_group_id")
	private FileGroupEntity fileGroupEntity;


	public PetResponseDTO toResponseDTO() {
		return PetResponseDTO.builder()
				.id(this.id)
				.type(this.type)
				.kind(this.kind)
				.gender(this.gender)
				.name(this.name)
				.birth(this.birth)
				.weight(this.weight)
				.allergy(this.allergy)
				.health(this.health)
				.neutered(this.neutered)
				.profileSrc(this.profileSrc)
				.uid(this.uid)
				.build();
	}

}
