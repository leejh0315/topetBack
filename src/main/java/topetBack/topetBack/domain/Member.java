package topetBack.topetBack.domain;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
public class Member {
	
	@Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
	private int memberId;
	
	private Long memberKid;
	
	private String memberName;
	
	private String memberEmail;
	private String activeUUID;
}
