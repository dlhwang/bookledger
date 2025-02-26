package com.test.bookledger.domain.model;

import com.test.bookledger.domain.emnum.BookStatus;
import lombok.Value;

import java.util.List;

@Value
public class BookModify {
    String bookId;
    String bookName;
    List<String> categoryIds;
    String authorId;
}
