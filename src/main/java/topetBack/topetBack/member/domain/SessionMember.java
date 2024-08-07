package topetBack.topetBack.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class SessionMember {
	private Long id;
	private String socialId;
	private String name;
	private String email;
	private String profileSrc;
	
	private List<PetResponseDTO> pets = new ArrayList<>();
	
    public Member toMember() {
    	return Member.builder()
    			.id(this.id)
    			.socialId(this.socialId)
    			.nickname(this.name)
    			.email(this.email)
    			.profileSrc(this.profileSrc)
//    			.pets(this.pets)
    			.build();
    			
    }
	
//	public MemberResponseDTO toResponseDTO() {
//		List<MemberResponseDTO> newMember = new ArrayList<>();
//        for (PetResponseDTO pet : this.pets) {
//            newM.add(pet.toResponseDTO());
//        }
//		
//		return MemberResponseDTO.builder()
//				.id(this.id)
//				.socialId(this.socialId)
//				.email(this.email)
//				.build();
//	}
	
}
