package topetBack.topetBack.pet.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.Member;

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

	@Column(nullable = false)
	@Comment("고유코드")
	private String UID;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "pet_member_relation", joinColumns = @JoinColumn(name = "pet_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private List<Member> member = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "file_group_id")
	private FileGroupEntity fileGroupEntity;

	public PetResponseDTO toResponseDTO() {
		return PetResponseDTO.builder().id(this.id).type(this.type).kind(this.kind).gender(this.gender).name(this.name)
				.birth(this.birth).member(this.member).weight(this.weight).allergy(this.allergy).health(this.health)
				.image(this.fileGroupEntity.getFileResponseDTOList()).build();
	}

//	    
//    @Builder
//    @ConstructorProperties({"type", "kind", "gender", "name",  "birth", "weigth", "allergy", "health"})
//	public PetEntity(String type, String kind, String gender , String name, Date birth, String weigth, String allergy, String health) {
//	    this.type = type;
//	    this.kind = kind;
//	    this.gender = gender;
//	    this.name = name;
//	    this.birth = birth;
//	    this.weigth = weigth;
//	    this.allergy=allergy;
//	    this.health=health;
//	}
//    
//    public PetEntity toEntity() {
//    	return PetEntity.builder()
//    			.type(type)
//    			.kind(kind)
//    			.gender(gender)
//    			.name(name)
//    			.build();
//    }
//    

//    public void addPhoto(Image image) {
//        this.image.add(image);
//    }    

}
