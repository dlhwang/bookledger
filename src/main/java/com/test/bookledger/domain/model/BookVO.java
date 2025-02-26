package com.test.bookledger.domain.model;

import com.test.bookledger.domain.emnum.BookStatus;
import com.test.global.model.enumtype.YN;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class BookVO {
    String bookId;
    String bookName;
    String categoryName;
    AuthorVO author;
    BookStatus bookStatus;
    YN deleteYN;

    public static BookVO to(Book book){
        return new BookVO(book.getId(), book.getName(), book.getCategoriesName(), AuthorVO.to(book.getAuthor()), book.getBookStatus(), book.getDeleteYN());
    }

}
