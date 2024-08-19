package topetBack.topetBack.likes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikes is a Querydsl query type for Likes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikes extends EntityPathBase<Likes> {

    private static final long serialVersionUID = -257853142L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikes likes = new QLikes("likes");

    public final topetBack.topetBack.member.domain.QMember author;

    public final topetBack.topetBack.community.domain.QCommunityEntity community;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final topetBack.topetBack.shorts.domain.QShortsEntity shorts;

    public QLikes(String variable) {
        this(Likes.class, forVariable(variable), INITS);
    }

    public QLikes(Path<? extends Likes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikes(PathMetadata metadata, PathInits inits) {
        this(Likes.class, metadata, inits);
    }

    public QLikes(Class<? extends Likes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new topetBack.topetBack.member.domain.QMember(forProperty("author")) : null;
        this.community = inits.isInitialized("community") ? new topetBack.topetBack.community.domain.QCommunityEntity(forProperty("community"), inits.get("community")) : null;
        this.shorts = inits.isInitialized("shorts") ? new topetBack.topetBack.shorts.domain.QShortsEntity(forProperty("shorts"), inits.get("shorts")) : null;
    }

}

