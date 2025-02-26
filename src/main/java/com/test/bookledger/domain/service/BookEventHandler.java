package com.test.bookledger.domain.service;

import com.test.bookledger.domain.model.Book;
import com.test.bookledger.domain.model.BookEvent;
import com.test.bookledger.domain.model.BookHistory;
import com.test.bookledger.domain.repository.BookHistoryRepository;
import com.test.bookledger.domain.repository.BookRepository;
import com.test.config.exception.DataNotFoundException;
import com.test.global.model.Events;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class BookEventHandler {

    private final BookRepository bookRepository;

    private final BookHistoryRepository bookHistoryRepository;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAfterCommitAsync(BookEvent event) {
        Book book = bookRepository.findById(event.getId()).orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));
        bookHistoryRepository.save(BookHistory.newInstance(book));
    }
}
