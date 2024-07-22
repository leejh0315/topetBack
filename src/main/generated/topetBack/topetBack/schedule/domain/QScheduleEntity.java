package topetBack.topetBack.schedule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleEntity is a Querydsl query type for ScheduleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleEntity extends EntityPathBase<ScheduleEntity> {

    private static final long serialVersionUID = 1093417235L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleEntity scheduleEntity = new QScheduleEntity("scheduleEntity");

    public final StringPath color = createString("color");

    public final NumberPath<Integer> editorId = createNumber("editorId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final topetBack.topetBack.file.domain.QFileGroupEntity fileGroupEntity;

    public final BooleanPath isComplete = createBoolean("isComplete");

    public final StringPath scheduleContent = createString("scheduleContent");

    public final NumberPath<Integer> scheduleId = createNumber("scheduleId", Integer.class);

    public final StringPath scheduleTitle = createString("scheduleTitle");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> writerId = createNumber("writerId", Integer.class);

    public QScheduleEntity(String variable) {
        this(ScheduleEntity.class, forVariable(variable), INITS);
    }

    public QScheduleEntity(Path<? extends ScheduleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleEntity(PathMetadata metadata, PathInits inits) {
        this(ScheduleEntity.class, metadata, inits);
    }

    public QScheduleEntity(Class<? extends ScheduleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fileGroupEntity = inits.isInitialized("fileGroupEntity") ? new topetBack.topetBack.file.domain.QFileGroupEntity(forProperty("fileGroupEntity")) : null;
    }

}

