package topetBack.topetBack.domain;

import lombok.Data;

@Data
public class MemberAndPet {
	private int memberId;
	private int petId;
	private String memberEmail;
	private String petName;
	private String petProfileSrc;
}
