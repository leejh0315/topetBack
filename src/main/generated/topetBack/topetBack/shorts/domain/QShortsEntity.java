package topetBack.topetBack.shorts.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShortsEntity is a Querydsl query type for ShortsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShortsEntity extends EntityPathBase<ShortsEntity> {

    private static final long serialVersionUID = -916298093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShortsEntity shortsEntity = new QShortsEntity("shortsEntity");

    public final topetBack.topetBack.member.domain.QMember author;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath thumbnailPhotoSrc = createString("thumbnailPhotoSrc");

    public final StringPath videoSrc = createString("videoSrc");

    public QShortsEntity(String variable) {
        this(ShortsEntity.class, forVariable(variable), INITS);
    }

    public QShortsEntity(Path<? extends ShortsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShortsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShortsEntity(PathMetadata metadata, PathInits inits) {
        this(ShortsEntity.class, metadata, inits);
    }

    public QShortsEntity(Class<? extends ShortsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new topetBack.topetBack.member.domain.QMember(forProperty("author")) : null;
    }

}

