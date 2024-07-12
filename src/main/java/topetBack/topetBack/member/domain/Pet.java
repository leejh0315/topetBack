package topetBack.topetBack.member.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Pet {
	private int petId;
	private String petType;
	private String petKind;
	private String petGender;
	private String petName;
	private LocalDateTime petBirth;
	private double petWeight;
	private String petAllergy;
	private String petHealth;
	private String petProfileSrc;
}