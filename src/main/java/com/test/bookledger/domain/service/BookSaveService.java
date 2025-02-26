package com.test.bookledger.domain.service;

import com.test.config.exception.DataNotFoundException;
import com.test.bookledger.domain.model.*;
import com.test.bookledger.domain.repository.AuthorRepository;
import com.test.bookledger.domain.repository.BookCategoryRepository;
import com.test.bookledger.domain.repository.BookRepository;
import com.test.bookledger.domain.repository.CategoryRepository;
import com.test.global.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookSaveService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Transactional
    public BookVO save(BookSave save){
        Author author = authorRepository.findById(save.getAuthorId()).orElseThrow(() -> new DataNotFoundException("지은이 정보가 존재하지 않습니다"));
        List<Category> categories = categoryRepository.findAllById(save.getCategoryIds());

        if(categories.isEmpty()){
            throw new IllegalArgumentException("신규 도서는 카테고리를 입력해야합니다.");
        }

        Book book = Book.newInstance(save.getBookName(), author, categories);
        book = bookRepository.save(book);
        Events.publishEvent(new BookEvent(book.getId()));
        return BookVO.to(book);
    }
}
