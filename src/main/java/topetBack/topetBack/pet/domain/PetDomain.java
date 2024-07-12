package topetBack.topetBack.pet.domain;

import java.beans.ConstructorProperties;
import java.util.Date;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
@ToString
@Entity
public class PetDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
	@Comment("반려동물 번호")
	private Long id;
		
	@CreationTimestamp
	@Comment("반려동물 타입")
	@Column(nullable = false)
	private String type;
		
	@UpdateTimestamp
	@Comment("반려동물 종")
	@Column(nullable = false)
    private String kind;	
		
	
	@Column(nullable = false)
	@Comment("반려동물 성별")
	private String gender;
		
    @Column(nullable = false)
	@Comment("반려동물 이름") 
	private String name;
	    
     @Column(nullable = true)
	 @Comment("반려동물 사진")
	 private String profileSrc;
	    
	 @Column(nullable = true)
     @Comment("생일")
	 private Date birth;
	    
	 @Column(nullable = true)
     @Comment("체중")
	 private String weigth;
	    
	 @Column(nullable = true)
     @Comment("알레르기")
	 private String allergy;
	 
	 @Column(nullable = true)
     @Comment("건강상태")
	 private String health;
	    
	
	// private File image;
//	 @OneToMany(
//	     mappedBy = "communityDomain",
//	     cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//	      orphanRemoval = true
//   )
	 //private List<Image> image = new ArrayList<>(); 
	    
	    
    @Builder
    @ConstructorProperties({"type", "kind", "gender", "name",  "birth", "weigth", "allergy", "health"})
	public PetDomain(String type, String kind, String gender , String name, Date birth, String weigth, String allergy, String health) {
	    this.type = type;
	    this.kind = kind;
	    this.gender = gender;
	    this.name = name;
	    this.birth = birth;
	    this.weigth = weigth;
	    this.allergy=allergy;
	    this.health=health;
	}
    
    public PetDomain toEntity() {
    	return PetDomain.builder()
    			.type(type)
    			.kind(kind)
    			.gender(gender)
    			.name(name)
    			.build();
    }
    

//    public void addPhoto(Image image) {
//        this.image.add(image);
//    }    
    
}
