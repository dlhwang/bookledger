package com.test.bookledger.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookHistory is a Querydsl query type for BookHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookHistory extends EntityPathBase<BookHistory> {

    private static final long serialVersionUID = -2138655063L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookHistory bookHistory = new QBookHistory("bookHistory");

    public final QBook book;

    public final QBookInfo bookInfo;

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public QBookHistory(String variable) {
        this(BookHistory.class, forVariable(variable), INITS);
    }

    public QBookHistory(Path<? extends BookHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookHistory(PathMetadata metadata, PathInits inits) {
        this(BookHistory.class, metadata, inits);
    }

    public QBookHistory(Class<? extends BookHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBook(forProperty("book"), inits.get("book")) : null;
        this.bookInfo = inits.isInitialized("bookInfo") ? new QBookInfo(forProperty("bookInfo")) : null;
    }

}

