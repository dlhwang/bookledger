package com.test.bookledger.domain.repository;

import com.test.bookledger.domain.model.Book;
import com.test.bookledger.domain.model.BookSearch;
import com.test.bookledger.domain.model.BookVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
    Page<BookVO> getBookList(BookSearch search, Pageable pageable);
}
