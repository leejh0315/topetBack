package topetBack.topetBack.member.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Getter
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
public class Member implements Serializable {

	@Id // 해당 변수가 PK가 됩니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
	private Long id;

	private String socialId;

	private String name;

	private String email;
	
	private String profileSrc;
	//@ManyToMany(cascade = { CascadeType.PERSIST })
	@ManyToMany(fetch=FetchType.LAZY
			, cascade = {
					CascadeType.ALL
//					, CascadeType.PERSIST
					}
	)
	@JoinTable(name = "pet_member_relation",
    	joinColumns = @JoinColumn(name = "member_id"),
	    inverseJoinColumns = @JoinColumn(name = "pet_id"))
//	@JsonManagedReference
	private List<PetEntity> pets = new ArrayList<>();


    @Builder
    public Member(Long id, String socialId, String email, String nickname) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = nickname;
    }
	
	public SessionMember toSessionMember() {
		List<PetResponseDTO> newPets = new ArrayList<>();
        for (PetEntity pet : this.pets) {
        	System.out.println(pet.getMember());
        	
            newPets.add(pet.toResponseDTO());
        }
		return SessionMember.builder()
				.id(this.id)
				.socialId(this.socialId)
				.name(this.name)
				.email(this.email)
				.pets(newPets)
				.build();
	}
	
	public MemberResponseDTO toResponseDTO() {
		
		return MemberResponseDTO.builder()
				.id(this.id)
				.socialId(this.socialId)
				.email(this.email)
				.name(this.name)
				.build();
				
		
	}
	

}
