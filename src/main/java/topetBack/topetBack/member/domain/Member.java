package topetBack.topetBack.member.domain;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.block.domain.BlockEntity;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.report.domain.ReportEntitiy;
import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.shorts.domain.ShortsEntity;

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
	
	@ColumnDefault("https://i.pinimg.com/564x/57/70/f0/5770f01a32c3c53e90ecda61483ccb08.jpg") 
	private String profileSrc;

	
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comment;
    
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<Likes> likes;
	
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<ShortsEntity> shorts;
	
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<CommunityEntity> community;
    

    
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<MemberPet> memberPet;
    
    
    @OneToMany(mappedBy = "blocker",cascade = CascadeType.REMOVE)
    private List<BlockEntity> block;
//    
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<ReportEntitiy> report;
    
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
    private List<ScheduleEntity> schedule;
	
    
    @Builder
    public Member(Long id, String socialId, String email, String nickname, String profileSrc, FileGroupEntity fileGroupEntity) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = nickname;
        this.profileSrc = profileSrc;
    }
	
//	public SessionMember toSessionMember() {
//		return SessionMember.builder()
//				.id(this.id)
//				.socialId(this.socialId)
//				.name(this.name)
//				.profileSrc(this.profileSrc)
//				.email(this.email)
//				.build();
//	}
	
	public MemberResponseDTO toResponseDTO() {
		
		return MemberResponseDTO.builder()
				.id(this.id)
//				.socialId(this.socialId)
//				.email(this.email)
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
