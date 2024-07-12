package topetBack.topetBack.file.domain;

import lombok.Getter;

@Getter
public enum FileCategory {
    COMMUNITY("community/"),
    SCHEDULE("schedule/");

    private final String path;

    FileCategory(String path) {
        this.path = path;
    }

}
