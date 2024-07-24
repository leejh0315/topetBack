
package topetBack.topetBack.pet.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.MemberResponseDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
	private List<FileResponseDTO> image = new ArrayList<>();

	private List<MemberResponseDTO> member = new ArrayList<MemberResponseDTO>();
 
}
