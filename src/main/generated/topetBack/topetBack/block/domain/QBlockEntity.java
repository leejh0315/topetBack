package topetBack.topetBack.block.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBlockEntity is a Querydsl query type for BlockEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBlockEntity extends EntityPathBase<BlockEntity> {

    private static final long serialVersionUID = -535714483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBlockEntity blockEntity = new QBlockEntity("blockEntity");

    public final topetBack.topetBack.member.domain.QMember blocked;

    public final topetBack.topetBack.member.domain.QMember blocker;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBlockEntity(String variable) {
        this(BlockEntity.class, forVariable(variable), INITS);
    }

    public QBlockEntity(Path<? extends BlockEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBlockEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBlockEntity(PathMetadata metadata, PathInits inits) {
        this(BlockEntity.class, metadata, inits);
    }

    public QBlockEntity(Class<? extends BlockEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blocked = inits.isInitialized("blocked") ? new topetBack.topetBack.member.domain.QMember(forProperty("blocked")) : null;
        this.blocker = inits.isInitialized("blocker") ? new topetBack.topetBack.member.domain.QMember(forProperty("blocker")) : null;
    }

}

