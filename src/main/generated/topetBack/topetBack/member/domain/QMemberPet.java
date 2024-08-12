package topetBack.topetBack.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPet is a Querydsl query type for MemberPet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPet extends EntityPathBase<MemberPet> {

    private static final long serialVersionUID = 337117609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPet memberPet = new QMemberPet("memberPet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final topetBack.topetBack.pet.domain.QPetEntity pet;

    public QMemberPet(String variable) {
        this(MemberPet.class, forVariable(variable), INITS);
    }

    public QMemberPet(Path<? extends MemberPet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPet(PathMetadata metadata, PathInits inits) {
        this(MemberPet.class, metadata, inits);
    }

    public QMemberPet(Class<? extends MemberPet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.pet = inits.isInitialized("pet") ? new topetBack.topetBack.pet.domain.QPetEntity(forProperty("pet")) : null;
    }

}

