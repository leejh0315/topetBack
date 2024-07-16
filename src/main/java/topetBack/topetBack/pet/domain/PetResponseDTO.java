
package topetBack.topetBack.pet.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetResponseDTO {
	
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
	 
	
}
