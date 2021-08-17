package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGame is a Querydsl query type for UserGame
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserGame extends EntityPathBase<UserGame> {

    private static final long serialVersionUID = -254875617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGame userGame = new QUserGame("userGame");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QGame game;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QUser user;

    public QUserGame(String variable) {
        this(UserGame.class, forVariable(variable), INITS);
    }

    public QUserGame(Path<? extends UserGame> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGame(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGame(PathMetadata metadata, PathInits inits) {
        this(UserGame.class, metadata, inits);
    }

    public QUserGame(Class<? extends UserGame> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.game = inits.isInitialized("game") ? new QGame(forProperty("game"), inits.get("game")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

