package com.test.bookledger.domain.service;

import com.test.bookledger.domain.emnum.BookStatus;
import com.test.config.exception.DataNotFoundException;
import com.test.bookledger.domain.model.*;
import com.test.bookledger.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookRentService {
    private final BookRepository bookRepository;

    @Transactional
    public BookVO rent(String bookId){
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));
        book.rent();
        return BookVO.to(book);
    }

    @Transactional
    public BookVO returnBook(String bookId, BookStatus bookStatus){
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));

        book.returnBook(bookStatus);

        return BookVO.to(book);
    }

    @Transactional
    public BookVO lost(String bookId){
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));

        book.lost();

        return BookVO.to(book);
    }

    @Transactional
    public BookVO damage(String bookId){
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));

        book.damage();

        return BookVO.to(book);
    }
}
