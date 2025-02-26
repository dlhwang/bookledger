package com.test.bookledger.domain.service;

import com.test.bookledger.domain.model.BookSearch;
import com.test.bookledger.domain.model.BookVO;
import com.test.bookledger.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class BookSearchService {

    private final BookRepository bookRepository;

    @Transactional
    public Page<BookVO> getBookList(BookSearch search, Pageable pageable){
        return bookRepository.getBookList(search, pageable);
    }
}
