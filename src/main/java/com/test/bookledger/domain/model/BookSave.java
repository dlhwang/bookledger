package com.test.bookledger.domain.model;

import lombok.Value;

import java.util.List;

@Value
public class BookSave {
    String bookName;
    List<String> categoryIds;
    String authorId;
}
