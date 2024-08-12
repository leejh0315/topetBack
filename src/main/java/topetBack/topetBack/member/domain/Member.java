package topetBack.topetBack.member.domain;

import java.io.Serializable;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileGroupEntity;
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


    @Builder
    public Member(Long id, String socialId, String email, String nickname, String profileSrc, FileGroupEntity fileGroupEntity) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = nickname;
        this.profileSrc = profileSrc;
    }
	
	public SessionMember toSessionMember() {
		return SessionMember.builder()
				.id(this.id)
				.socialId(this.socialId)
				.name(this.name)
				.profileSrc(this.profileSrc)
				.email(this.email)
				.build();
	}
	
	public MemberResponseDTO toResponseDTO() {
		
		return MemberResponseDTO.builder()
				.id(this.id)
				.socialId(this.socialId)
				.email(this.email)
				.name(this.name)
				.profileSrc(this.profileSrc)
				.build();
	}

	public MemberSummaryResponseDTO toSummaryResponseDTO() {
		return MemberSummaryResponseDTO.builder()
				.id(this.id)
				.name(this.name)
				.src(this.profileSrc)
				.build();
	}

	public void updateMember(MemberRequestDTO memberRequestDTO){
		if(memberRequestDTO.getName() != null){
			this.name = memberRequestDTO.getName();
		}
		if(memberRequestDTO.getProfileSrc() != null){
			this.profileSrc = memberRequestDTO.getProfileSrc();
		}
	}



}
