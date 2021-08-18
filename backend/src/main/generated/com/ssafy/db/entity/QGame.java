package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGame is a Querydsl query type for Game
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGame extends EntityPathBase<Game> {

    private static final long serialVersionUID = 846108340L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGame game = new QGame("game");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QConference conference;

    public final QGameCategory gameCategory;

    public final DateTimePath<java.time.LocalDateTime> gameEndTime = createDateTime("gameEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> gameStartTime = createDateTime("gameStartTime", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public QGame(String variable) {
        this(Game.class, forVariable(variable), INITS);
    }

    public QGame(Path<? extends Game> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGame(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGame(PathMetadata metadata, PathInits inits) {
        this(Game.class, metadata, inits);
    }

    public QGame(Class<? extends Game> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conference = inits.isInitialized("conference") ? new QConference(forProperty("conference"), inits.get("conference")) : null;
        this.gameCategory = inits.isInitialized("gameCategory") ? new QGameCategory(forProperty("gameCategory")) : null;
    }

}

