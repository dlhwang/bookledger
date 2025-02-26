package com.test.bookledger.domain.model;

import com.test.bookledger.application.dto.BookRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookSearch {
    String categoryId;
    String categoryName;
    String authorId;
    String authorName;
    String bookName;

    public static BookSearch to(BookRequest.BookSearch search){
        return new BookSearch(search.getCategoryId(), search.getCategoryName(), search.getAuthorId(), search.getAuthorName(), search.getBookName());
    }
}
