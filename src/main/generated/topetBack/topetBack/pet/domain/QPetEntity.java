package topetBack.topetBack.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPetEntity is a Querydsl query type for PetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetEntity extends EntityPathBase<PetEntity> {

    private static final long serialVersionUID = -445351219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPetEntity petEntity = new QPetEntity("petEntity");

    public final StringPath allergy = createString("allergy");

    public final StringPath birth = createString("birth");

    public final topetBack.topetBack.file.domain.QFileGroupEntity fileGroupEntity;

    public final StringPath gender = createString("gender");

    public final StringPath health = createString("health");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kind = createString("kind");

    public final StringPath name = createString("name");

    public final StringPath profileSrc = createString("profileSrc");

    public final StringPath type = createString("type");

    public final StringPath UID = createString("UID");

    public final StringPath weight = createString("weight");

    public QPetEntity(String variable) {
        this(PetEntity.class, forVariable(variable), INITS);
    }

    public QPetEntity(Path<? extends PetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPetEntity(PathMetadata metadata, PathInits inits) {
        this(PetEntity.class, metadata, inits);
    }

    public QPetEntity(Class<? extends PetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fileGroupEntity = inits.isInitialized("fileGroupEntity") ? new topetBack.topetBack.file.domain.QFileGroupEntity(forProperty("fileGroupEntity")) : null;
    }

}

