package topetBack.topetBack.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileInfoEntity is a Querydsl query type for FileInfoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileInfoEntity extends EntityPathBase<FileInfoEntity> {

    private static final long serialVersionUID = 1477873835L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileInfoEntity fileInfoEntity = new QFileInfoEntity("fileInfoEntity");

    public final QFileGroupEntity fileGroupEntity;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath newFileName = createString("newFileName");

    public final StringPath origFileName = createString("origFileName");

    public QFileInfoEntity(String variable) {
        this(FileInfoEntity.class, forVariable(variable), INITS);
    }

    public QFileInfoEntity(Path<? extends FileInfoEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileInfoEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileInfoEntity(PathMetadata metadata, PathInits inits) {
        this(FileInfoEntity.class, metadata, inits);
    }

    public QFileInfoEntity(Class<? extends FileInfoEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fileGroupEntity = inits.isInitialized("fileGroupEntity") ? new QFileGroupEntity(forProperty("fileGroupEntity")) : null;
    }

}

