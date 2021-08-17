package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWinRate is a Querydsl query type for WinRate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWinRate extends EntityPathBase<WinRate> {

    private static final long serialVersionUID = 795141338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWinRate winRate = new QWinRate("winRate");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> firstRanked = createNumber("firstRanked", Integer.class);

    public final QGameCategory gameCategory;

    public final NumberPath<Integer> gameCount = createNumber("gameCount", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QUser user;

    public QWinRate(String variable) {
        this(WinRate.class, forVariable(variable), INITS);
    }

    public QWinRate(Path<? extends WinRate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWinRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWinRate(PathMetadata metadata, PathInits inits) {
        this(WinRate.class, metadata, inits);
    }

    public QWinRate(Class<? extends WinRate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameCategory = inits.isInitialized("gameCategory") ? new QGameCategory(forProperty("gameCategory")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

