package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameHistory is a Querydsl query type for GameHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGameHistory extends EntityPathBase<GameHistory> {

    private static final long serialVersionUID = 1793635136L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameHistory gameHistory = new QGameHistory("gameHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> action = createNumber("action", Integer.class);

    public final QGame game;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> insertedTime = createDateTime("insertedTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> ranking = createNumber("ranking", Integer.class);

    public final QUser user;

    public QGameHistory(String variable) {
        this(GameHistory.class, forVariable(variable), INITS);
    }

    public QGameHistory(Path<? extends GameHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameHistory(PathMetadata metadata, PathInits inits) {
        this(GameHistory.class, metadata, inits);
    }

    public QGameHistory(Class<? extends GameHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.game = inits.isInitialized("game") ? new QGame(forProperty("game"), inits.get("game")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

