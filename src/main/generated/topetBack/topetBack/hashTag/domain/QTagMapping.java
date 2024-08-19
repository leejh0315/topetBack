package topetBack.topetBack.hashTag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagMapping is a Querydsl query type for TagMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagMapping extends EntityPathBase<TagMapping> {

    private static final long serialVersionUID = 1670347926L;

    public static final QTagMapping tagMapping = new QTagMapping("tagMapping");

    public final SetPath<HashTagEntity, QHashTagEntity> hashTag = this.<HashTagEntity, QHashTagEntity>createSet("hashTag", HashTagEntity.class, QHashTagEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QTagMapping(String variable) {
        super(TagMapping.class, forVariable(variable));
    }

    public QTagMapping(Path<? extends TagMapping> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagMapping(PathMetadata metadata) {
        super(TagMapping.class, metadata);
    }

}

