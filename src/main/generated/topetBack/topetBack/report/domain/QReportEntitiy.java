package topetBack.topetBack.report.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportEntitiy is a Querydsl query type for ReportEntitiy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportEntitiy extends EntityPathBase<ReportEntitiy> {

    private static final long serialVersionUID = -1742454692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportEntitiy reportEntitiy = new QReportEntitiy("reportEntitiy");

    public final topetBack.topetBack.member.domain.QMember author;

    public final topetBack.topetBack.comment.domain.QCommentEntity comment;

    public final topetBack.topetBack.community.domain.QCommunityEntity community;

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reason = createString("reason");

    public QReportEntitiy(String variable) {
        this(ReportEntitiy.class, forVariable(variable), INITS);
    }

    public QReportEntitiy(Path<? extends ReportEntitiy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportEntitiy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportEntitiy(PathMetadata metadata, PathInits inits) {
        this(ReportEntitiy.class, metadata, inits);
    }

    public QReportEntitiy(Class<? extends ReportEntitiy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new topetBack.topetBack.member.domain.QMember(forProperty("author")) : null;
        this.comment = inits.isInitialized("comment") ? new topetBack.topetBack.comment.domain.QCommentEntity(forProperty("comment"), inits.get("comment")) : null;
        this.community = inits.isInitialized("community") ? new topetBack.topetBack.community.domain.QCommunityEntity(forProperty("community"), inits.get("community")) : null;
    }

}

