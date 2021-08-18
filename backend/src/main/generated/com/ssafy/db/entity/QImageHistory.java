package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageHistory is a Querydsl query type for ImageHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QImageHistory extends EntityPathBase<ImageHistory> {

    private static final long serialVersionUID = -1827074181L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageHistory imageHistory = new QImageHistory("imageHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QGame game;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath image = createString("image");

    public final DateTimePath<java.time.LocalDateTime> insertedTime = createDateTime("insertedTime", java.time.LocalDateTime.class);

    public final StringPath keyword = createString("keyword");

    public QImageHistory(String variable) {
        this(ImageHistory.class, forVariable(variable), INITS);
    }

    public QImageHistory(Path<? extends ImageHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageHistory(PathMetadata metadata, PathInits inits) {
        this(ImageHistory.class, metadata, inits);
    }

    public QImageHistory(Class<? extends ImageHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.game = inits.isInitialized("game") ? new QGame(forProperty("game"), inits.get("game")) : null;
    }

}

