package topetBack.topetBack.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityEntity is a Querydsl query type for CommunityEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityEntity extends EntityPathBase<CommunityEntity> {

    private static final long serialVersionUID = 652431181L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityEntity communityEntity = new QCommunityEntity("communityEntity");

    public final StringPath animal = createString("animal");

    public final topetBack.topetBack.member.domain.QMember author;

    public final StringPath category = createString("category");

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final topetBack.topetBack.file.domain.QFileGroupEntity fileGroupEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final SetPath<topetBack.topetBack.likes.domain.Likes, topetBack.topetBack.likes.domain.QLikes> likesList = this.<topetBack.topetBack.likes.domain.Likes, topetBack.topetBack.likes.domain.QLikes>createSet("likesList", topetBack.topetBack.likes.domain.Likes.class, topetBack.topetBack.likes.domain.QLikes.class, PathInits.DIRECT2);

    public final topetBack.topetBack.hashTag.domain.QTagMapping tagMappings;
    
    public final topetBack.topetBack.hashTag.domain.QHashTagEntity hashTagEntity;

    public final StringPath title = createString("title");

    public StringPath tag = createString("tag");
    
    public final DateTimePath<java.time.LocalDateTime> updatedTime = createDateTime("updatedTime", java.time.LocalDateTime.class);

    public QCommunityEntity(String variable) {
        this(CommunityEntity.class, forVariable(variable), INITS);
    }

    public QCommunityEntity(Path<? extends CommunityEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityEntity(PathMetadata metadata, PathInits inits) {
        this(CommunityEntity.class, metadata, inits);
    }

    public QCommunityEntity(Class<? extends CommunityEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new topetBack.topetBack.member.domain.QMember(forProperty("author")) : null;
        this.fileGroupEntity = inits.isInitialized("fileGroupEntity") ? new topetBack.topetBack.file.domain.QFileGroupEntity(forProperty("fileGroupEntity")) : null;
        this.tagMappings = inits.isInitialized("tagMappings") ? new topetBack.topetBack.hashTag.domain.QTagMapping(forProperty("tagMappings")) : null;
        this.hashTagEntity = inits.isInitialized("hashTagEntity") ? new topetBack.topetBack.hashTag.domain.QHashTagEntity(forProperty("hashTagEntity")):null;
        this.tag = this.hashTagEntity.tag;
    }

}

