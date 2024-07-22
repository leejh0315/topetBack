package topetBack.topetBack.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPetEntity is a Querydsl query type for PetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetEntity extends EntityPathBase<PetEntity> {

    private static final long serialVersionUID = -445351219L;

    public static final QPetEntity petEntity = new QPetEntity("petEntity");

    public final StringPath allergy = createString("allergy");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final StringPath gender = createString("gender");

    public final StringPath health = createString("health");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kind = createString("kind");

    public final StringPath name = createString("name");

    public final StringPath profileSrc = createString("profileSrc");

    public final StringPath type = createString("type");

    public final StringPath weight = createString("weight");

    public QPetEntity(String variable) {
        super(PetEntity.class, forVariable(variable));
    }

    public QPetEntity(Path<? extends PetEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPetEntity(PathMetadata metadata) {
        super(PetEntity.class, metadata);
    }

}

