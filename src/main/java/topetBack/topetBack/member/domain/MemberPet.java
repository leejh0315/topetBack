package topetBack.topetBack.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import topetBack.topetBack.pet.domain.PetEntity;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private PetEntity pet;

    public MemberPet(Member member, PetEntity pet) {
        this.member = member;
        this.pet = pet;
    }

}
