package com.test.bookledger.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bookledger.domain.model.Book;
import com.test.bookledger.domain.model.BookSearch;

import com.test.bookledger.domain.model.BookVO;
import com.test.global.model.enumtype.YN;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static com.test.bookledger.domain.model.QBook.book;
import static com.test.bookledger.domain.model.QAuthor.author;
import static com.test.bookledger.domain.model.QBookCategory.bookCategory;
import static com.test.bookledger.domain.model.QCategory.category;


public class BookRepositoryCustomImpl extends QuerydslRepositorySupport implements BookRepositoryCustom{

    private JPQLQueryFactory queryFactory;

    public BookRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(Book.class);
        queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<BookVO> getBookList(BookSearch search, Pageable pageable) {
        JPQLQuery<Book> query = queryFactory.selectFrom(book)
                .innerJoin(book.author, author).fetchJoin()
                .leftJoin(book.bookCategories, bookCategory)
                .leftJoin(bookCategory.category, category)
                .where(applyNormalCondition(), applyCondition(search))
                .groupBy(book.id);

        if (pageable != null && pageable.isPaged()) {
            applyPageable(query, pageable);
        }

        List<Book> fetch = query.fetch();

        return new PageImpl<>(fetch.stream().map(BookVO::to).toList(), pageable, query.fetchCount());
    }

    private BooleanExpression applyNormalCondition() {
        return book.deleteYN.eq(YN.N);
    }

    private BooleanBuilder applyCondition(BookSearch search) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(StringUtils.hasText(search.getCategoryId())){
            booleanBuilder.and(bookCategory.category.id.eq(search.getCategoryId()));
        }
        if(StringUtils.hasText(search.getCategoryName())){
            booleanBuilder.and(bookCategory.category.name.containsIgnoreCase(search.getCategoryName()));
        }
        if(StringUtils.hasText(search.getAuthorId())){
            booleanBuilder.and(author.id.eq(search.getAuthorId()));
        }
        if(StringUtils.hasText(search.getAuthorName())){
            booleanBuilder.and(author.name.containsIgnoreCase(search.getAuthorName()));
        }
        if(StringUtils.hasText(search.getBookName())){
            booleanBuilder.and(book.name.containsIgnoreCase(search.getBookName()));
        }

        return booleanBuilder;
    }

    private void applyPageable(JPQLQuery<Book> query, Pageable pageable) {
        if (pageable != null && pageable.isPaged()) {
            query.offset((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize());
        }
    }
}
