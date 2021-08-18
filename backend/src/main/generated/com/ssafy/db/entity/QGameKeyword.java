package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameKeyword is a Querydsl query type for GameKeyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGameKeyword extends EntityPathBase<GameKeyword> {

    private static final long serialVersionUID = 52292757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameKeyword gameKeyword = new QGameKeyword("gameKeyword");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QGameCategory gameCategory;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath keyword = createString("keyword");

    public QGameKeyword(String variable) {
        this(GameKeyword.class, forVariable(variable), INITS);
    }

    public QGameKeyword(Path<? extends GameKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameKeyword(PathMetadata metadata, PathInits inits) {
        this(GameKeyword.class, metadata, inits);
    }

    public QGameKeyword(Class<? extends GameKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameCategory = inits.isInitialized("gameCategory") ? new QGameCategory(forProperty("gameCategory")) : null;
    }

}

