package topetBack.topetBack.file.domain;

import lombok.Getter;

@Getter
public enum FileCategory {
    COMMUNITY("community/"),
    SCHEDULE("schedule/"),
	PET("pet/"),
	MEMBER("member/");
	
    private final String path;

    FileCategory(String path) {
        this.path = path;
    }

}
