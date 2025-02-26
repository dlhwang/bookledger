package com.test.bookledger.application.service;

import com.test.bookledger.application.dto.BookRequest;
import com.test.bookledger.application.dto.BookResponse;
import com.test.bookledger.domain.service.BookModifyService;
import com.test.bookledger.domain.service.BookRentService;
import com.test.bookledger.domain.service.BookSaveService;
import com.test.bookledger.domain.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookLedgerService {

    private final BookSaveService bookSaveService;
    private final BookModifyService bookModifyService;
    private final BookSearchService bookSearchService;
    private final BookRentService bookRentService;

    public BookResponse getAll(BookRequest.BookSearch search, Pageable pageable){
        return BookResponse.to(bookSearchService.getBookList(search.to(), pageable));
    }

    public BookResponse.BookVO save(BookRequest.BookSave bookSave){
        return BookResponse.BookVO.to(bookSaveService.save(bookSave.to()));
    }

    public BookResponse.BookVO save(BookRequest.BookModify bookModify){
        return BookResponse.BookVO.to(bookModifyService.modify(bookModify.to()));
    }

    public BookResponse.BookVO rent(String id) {
        return BookResponse.BookVO.to(bookRentService.rent(id));
    }


    public BookResponse.BookVO lost(String id) {
        return BookResponse.BookVO.to(bookRentService.lost(id));
    }

    public BookResponse.BookVO damage(String id) {
        return BookResponse.BookVO.to(bookRentService.damage(id));
    }

    public BookResponse.BookVO returnBook(String id, BookRequest.BookRent bookRent) {
        return BookResponse.BookVO.to(bookRentService.returnBook(id, bookRent.getBookStatus()));
    }
}
