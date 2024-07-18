package topetBack.topetBack.pet.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.Member;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PetRequestDTO {
	
	private Long id;
	private String type;
    private String kind;	
	private String gender;
	
	private String name;
	private List<Member> member;
	private String birth;
	private String weight;
	private String allergy;
	private String health;
	
	private String UID;
	
	private List<MultipartFile> image;
	
	
	public PetEntity toPetEntity() {
		FileGroupEntity fileGroupEntity = new FileGroupEntity();
		return PetEntity.builder()
	            .id(this.id)
                .type(this.type)
                .kind(this.kind)
                .gender(this.gender)
                .member(this.member)
                .name(this.name)
                .birth(this.birth)
                .weight(this.weight)
                .allergy(this.allergy)
                .health(this.health)
                .UID(this.UID)
                .fileGroupEntity(fileGroupEntity)
                .build();
	}

	
}