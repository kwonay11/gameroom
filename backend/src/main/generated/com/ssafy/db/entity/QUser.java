package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<ConferenceHistory, QConferenceHistory> conferenceHistories = this.<ConferenceHistory, QConferenceHistory>createList("conferenceHistories", ConferenceHistory.class, QConferenceHistory.class, PathInits.DIRECT2);

    public final ListPath<Conference, QConference> conferences = this.<Conference, QConference>createList("conferences", Conference.class, QConference.class, PathInits.DIRECT2);

    public final NumberPath<Integer> exp = createNumber("exp", Integer.class);

    public final ListPath<GameHistory, QGameHistory> gameHistories = this.<GameHistory, QGameHistory>createList("gameHistories", GameHistory.class, QGameHistory.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<UserConference, QUserConference> userConferences = this.<UserConference, QUserConference>createList("userConferences", UserConference.class, QUserConference.class, PathInits.DIRECT2);

    public final ListPath<UserGame, QUserGame> userGames = this.<UserGame, QUserGame>createList("userGames", UserGame.class, QUserGame.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final ListPath<WinRate, QWinRate> winRates = this.<WinRate, QWinRate>createList("winRates", WinRate.class, QWinRate.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

