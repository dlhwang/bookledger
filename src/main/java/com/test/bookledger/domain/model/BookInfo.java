package com.test.bookledger.domain.model;

import com.test.bookledger.domain.emnum.BookStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookInfo {

    /**
     * 책 제목
     */
    @Column(name = "book_name", nullable = false)
    private String bookName;

    /**
     * 지은이
     */
    @Column(name = "author_name", nullable = false)
    private String authorName;

    /**
     * 책 대여 상태
     */
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private BookInfo(String bookName, String authorName, BookStatus status) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.status = status;
    }

    public static BookInfo newInstance(String bookName, String authorName, BookStatus status) {
        return new BookInfo(bookName, authorName, status);
    }
}
