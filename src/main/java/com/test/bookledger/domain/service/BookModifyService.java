package com.test.bookledger.domain.service;

import com.test.config.exception.DataNotFoundException;
import com.test.bookledger.domain.model.*;
import com.test.bookledger.domain.repository.AuthorRepository;
import com.test.bookledger.domain.repository.BookCategoryRepository;
import com.test.bookledger.domain.repository.BookRepository;
import com.test.bookledger.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookModifyService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Transactional
    public BookVO modify(BookModify modify) {
        final Book book = bookRepository.findById(modify.getBookId())
                .orElseThrow(() -> new DataNotFoundException("존재하지 않은 도서입니다."));

        // 요청으로 지은이가 존재한다면 변경으로 간주
        if(StringUtils.hasText(modify.getAuthorId())){
            Author author = authorRepository.findById(modify.getAuthorId())
                    .orElseThrow(() -> new DataNotFoundException("존재하지 않는 지은이 입니다."));

            book.changeAuthor(author);
        }

        // 요청으로 제목이 존재한다면 변경으로 간주
        if(StringUtils.hasText(modify.getBookName())){
            book.changeName(modify.getBookName());
        }


        // 요청으로 카테고리가 존재한다면 변경으로 간주
        if(!ObjectUtils.isEmpty(modify.getCategoryIds())){
            List<Category> categories = categoryRepository.findAllById(modify.getCategoryIds());
            book.changeCategory(
                    categories.stream().map(
                            category -> BookCategory.newInstance(category, book)).toList());
        }
        bookRepository.save(book);
        return BookVO.to(book);
    }
}
