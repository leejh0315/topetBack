package topetBack.topetBack.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileGroupEntity is a Querydsl query type for FileGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileGroupEntity extends EntityPathBase<FileGroupEntity> {

    private static final long serialVersionUID = -1809030136L;

    public static final QFileGroupEntity fileGroupEntity = new QFileGroupEntity("fileGroupEntity");

    public final ListPath<FileInfoEntity, QFileInfoEntity> fileList = this.<FileInfoEntity, QFileInfoEntity>createList("fileList", FileInfoEntity.class, QFileInfoEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QFileGroupEntity(String variable) {
        super(FileGroupEntity.class, forVariable(variable));
    }

    public QFileGroupEntity(Path<? extends FileGroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileGroupEntity(PathMetadata metadata) {
        super(FileGroupEntity.class, metadata);
    }

}

