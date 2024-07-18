
package topetBack.topetBack.pet.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Data
@Builder
public class PetResponseDTO {
	
	private Long id;
	private String type;
    private String kind;	
	private String gender;
	private String name;
	private String profileSrc;
	private String birth;
	private String weight;
	private String allergy;
	private String health;
	private List<FileResponseDTO> image;
	private List<Member> member;
	 
	
}
