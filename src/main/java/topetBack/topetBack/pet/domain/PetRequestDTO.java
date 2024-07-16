package topetBack.topetBack.pet.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetRequestDTO {
	
	private Long id;
	private String type;
    private String kind;	
	private String gender;
	
	private String name;
	private String profileSrc;
	private Date birth;
	private String weight;
	private String allergy;
	private String health;
	    
	private MultipartFile image;
	
	
	public PetEntity toPetEntity() {
		return PetEntity.builder()
	            .id(this.id)
                .type(this.type)
                .kind(this.kind)
                .gender(this.gender)
                .name(this.name)
                .birth(this.birth)
                .weight(this.weight)
                .allergy(this.allergy)
                .health(this.health)
                .build();
	}

	
}