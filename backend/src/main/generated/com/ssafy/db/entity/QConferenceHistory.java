package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConferenceHistory is a Querydsl query type for ConferenceHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConferenceHistory extends EntityPathBase<ConferenceHistory> {

    private static final long serialVersionUID = -321961898L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConferenceHistory conferenceHistory = new QConferenceHistory("conferenceHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> action = createNumber("action", Integer.class);

    public final QConference conference;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> insertedTime = createDateTime("insertedTime", java.time.LocalDateTime.class);

    public final QUser user;

    public QConferenceHistory(String variable) {
        this(ConferenceHistory.class, forVariable(variable), INITS);
    }

    public QConferenceHistory(Path<? extends ConferenceHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConferenceHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConferenceHistory(PathMetadata metadata, PathInits inits) {
        this(ConferenceHistory.class, metadata, inits);
    }

    public QConferenceHistory(Class<? extends ConferenceHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conference = inits.isInitialized("conference") ? new QConference(forProperty("conference"), inits.get("conference")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

