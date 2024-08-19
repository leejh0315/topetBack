package topetBack.topetBack.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = -144731571L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final topetBack.topetBack.member.domain.QMember author;

    public final ListPath<CommentEntity, QCommentEntity> children = this.<CommentEntity, QCommentEntity>createList("children", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    public final topetBack.topetBack.community.domain.QCommunityEntity community;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCommentEntity parent;

    public final topetBack.topetBack.shorts.domain.QShortsEntity shorts;

    public final DateTimePath<java.time.LocalDateTime> updatedTime = createDateTime("updatedTime", java.time.LocalDateTime.class);

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new topetBack.topetBack.member.domain.QMember(forProperty("author")) : null;
        this.community = inits.isInitialized("community") ? new topetBack.topetBack.community.domain.QCommunityEntity(forProperty("community"), inits.get("community")) : null;
        this.parent = inits.isInitialized("parent") ? new QCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.shorts = inits.isInitialized("shorts") ? new topetBack.topetBack.shorts.domain.QShortsEntity(forProperty("shorts"), inits.get("shorts")) : null;
    }

}

