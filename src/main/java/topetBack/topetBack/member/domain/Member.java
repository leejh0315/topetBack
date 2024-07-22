package topetBack.topetBack.member.domain;


import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
public class Member implements Serializable{

    @Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
    private int id;

    
    private String socialId;

    private String name;

    private String email;


    @Builder
    public Member(int id, String socialId, String email, String nickname) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = nickname;
    }

}
