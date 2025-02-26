package com.test.bookledger.application.service;

import com.test.bookledger.application.dto.BookRequest;
import com.test.bookledger.application.dto.BookResponse;
import com.test.bookledger.domain.emnum.BookStatus;
import com.test.bookledger.domain.model.Author;
import com.test.bookledger.domain.model.Book;
import com.test.bookledger.domain.model.Category;
import com.test.bookledger.domain.repository.AuthorRepository;
import com.test.bookledger.domain.repository.BookRepository;
import com.test.bookledger.domain.repository.CategoryRepository;
import com.test.config.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional // 각 테스트가 트랜잭션 내에서 실행되도록 설정
class BookLedgerServiceTest {

    @Autowired
    BookLedgerService bookLedgerService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;


    @Test
    @DisplayName("도서를 조회할 수 있다")
    void getAll() {
        // Given
        BookRequest.BookSearch search = new BookRequest.BookSearch();
        search.setBookName("");

        // When
        BookResponse bookResponse = bookLedgerService.getAll(search, Pageable.unpaged());

        // Then
        assertNotNull(bookResponse, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertNotNull(bookResponse.getBookList(), "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
    }

    @Test
    @DisplayName("카테고리별로 도서를 검색 할 수 있다")
    void getAllByCategory() {
        // Given
        BookRequest.BookSearch search = new BookRequest.BookSearch();
        search.setCategoryName("과학");

        // When
        BookResponse bookResponse = bookLedgerService.getAll(search, Pageable.unpaged());

        // Then
        assertNotNull(bookResponse, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertNotNull(bookResponse.getBookList(), "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertTrue(bookResponse.getBookList()
                .stream().findAny()
                .stream().allMatch(bookVO -> bookVO.getCategoryName().contains("과학"))
                , "카테고리로 조회시 모두 같은 카테고리여야합니다.");
    }

    @Test
    @DisplayName("지은이와 제목으로 도서를 검색할 수 있다")
    void getAllByAuthorAndBookName() {
        // Given
        BookRequest.BookSearch search = new BookRequest.BookSearch();
        search.setAuthorName("현영서");
        search.setBookName("배부르게");

        // When
        BookResponse bookResponse = bookLedgerService.getAll(search, Pageable.unpaged());

        // Then
        assertNotNull(bookResponse, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertNotNull(bookResponse.getBookList(), "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertTrue(bookResponse.getBookList()
                .stream().findAny()
                .stream().allMatch(bookVO -> bookVO.getAuthor().getName().contains("현영서"))
        , "지은이로 검색시 지은이가 포함된 도서여야 합니다.");
        assertTrue(bookResponse.getBookList()
                .stream().findAny()
                .stream().allMatch(bookVO -> bookVO.getBookName().contains("배부르게"))
        , "제목으로 검색시 제목이 포함된 도서여야 합니다.");
    }

    @Test
    @DisplayName("도서를 등록할 수 있다.")
    void save() {
        // Given
        Category category = categoryRepository.findAll().stream().findAny().orElseThrow(() -> new DataNotFoundException("데이터가 없습니다."));
        Author author = authorRepository.findAll().stream().findAny().orElseThrow(() -> new DataNotFoundException("데이터가 없습니다."));
        BookRequest.BookSave bookSave = new BookRequest.BookSave();
        bookSave.setAuthorId(author.getId());
        bookSave.setCategoryIds(List.of(category.getId()));
        bookSave.setBookName("도둑맞은 집중력");

        // When
        BookResponse.BookVO saved = bookLedgerService.save(bookSave);

        // Then
        assertNotNull(saved, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");

    }

    @Test
    @DisplayName("카테고리가 없을 시 신규 도서로 등록할 수 없다.")
    void shouldThrowExceptionWhenCategoryIsNull() {
        // Given
        Author author = authorRepository.findAll().stream().findAny().orElseThrow(() -> new DataNotFoundException("데이터가 없습니다."));
        BookRequest.BookSave bookSave = new BookRequest.BookSave();
        bookSave.setAuthorId(author.getId());
        bookSave.setCategoryIds(List.of());
        bookSave.setBookName("도둑맞은 집중력");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookLedgerService.save(bookSave), "신규 도서는 항상 카테고리가 필요");
    }

    @Test
    @DisplayName("도서는 카테고리가 변경될 수 있다.")
    void canChangeCategory() {
        // Given
        Book book = bookRepository.findAll().stream().findAny().orElseThrow();
        Category category = categoryRepository.findAll().stream().findAny().orElseThrow(() -> new DataNotFoundException("데이터가 없습니다."));
        BookRequest.BookModify bookModify = new BookRequest.BookModify();

        bookModify.setBookId(book.getId());
        bookModify.setCategoryIds(List.of(category.getId()));

        // When
        BookResponse.BookVO saved = bookLedgerService.save(bookModify);

        // Then
        assertNotNull(saved, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertTrue(saved.getCategoryName().contains(category.getName()), "카테고리가 변경되지 않았습니다.");

    }


    @Test
    @DisplayName("도서는 대여할 수 있다.")
    void rent() {
        // Given
        Book book = bookRepository.findAllByBookStatus(BookStatus.AVAILABLE).stream().findAny().orElseThrow();

        // When
        BookResponse.BookVO bookVO = bookLedgerService.rent(book.getId());

        // Then
        assertNotNull(bookVO, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertSame(BookStatus.RENTED, bookVO.getBookStatus());
    }

    @Test
    @DisplayName("도서는 분실로 대여가 중단될 수 있다.")
    void shouldThrowExceptionWhenBookLost() {
        // Given
        Book book = bookRepository.findAllByBookStatus(BookStatus.LOST).stream().findAny().orElseThrow();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookLedgerService.rent(book.getId()));
    }

    @Test
    @DisplayName("도서는 훼손으로 대여가 중단될 수 있다.")
    void shouldThrowExceptionWhenBookDamage() {
        // Given
        Book book = bookRepository.findAllByBookStatus(BookStatus.DAMAGED).stream().findAny().orElseThrow();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookLedgerService.rent(book.getId()));
    }

    @Test
    @DisplayName("어느 도서든 분실될 수 있다.")
    void lost() {
        // Given
        Book book = bookRepository.findAll().stream().findAny().orElseThrow();

        // When
        BookResponse.BookVO bookVO = bookLedgerService.lost(book.getId());

        // Then
        assertNotNull(bookVO, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertSame(BookStatus.LOST, bookVO.getBookStatus());

    }

    @Test
    @DisplayName("어느 도서든 훼손될 수 있다.")
    void damage() {
        // Given
        Book book = bookRepository.findAll().stream().findAny().orElseThrow();

        // When
        BookResponse.BookVO bookVO = bookLedgerService.damage(book.getId());

        // Then
        assertNotNull(bookVO, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertSame(BookStatus.DAMAGED, bookVO.getBookStatus());
    }

    @Test
    @DisplayName("도서를 반납할 수 있다.")
    void returnBook() {
        // Given
        Book book = bookRepository.findAllByBookStatus(BookStatus.RENTED).stream().findAny().orElseThrow();
        BookRequest.BookRent bookRent =  new BookRequest.BookRent();
        bookRent.setBookId(book.getId());
        bookRent.setBookStatus(BookStatus.AVAILABLE);

        // When
        BookResponse.BookVO bookVO = bookLedgerService.returnBook(book.getId(), bookRent);

        // Then
        assertNotNull(bookVO, "객체가 null이 아니어야 합니다. null 값이 반환되었습니다.");
        assertSame(BookStatus.AVAILABLE, bookVO.getBookStatus());
    }

    @Test
    @DisplayName("대여하지 않은 도서는 반납할 수 없다.")
    void shouldThrowExceptionWhenBookWasNotRented() {
        // Given
        Book book = bookRepository.findAllByBookStatus(BookStatus.AVAILABLE).stream().findAny().orElseThrow();
        BookRequest.BookRent bookRent =  new BookRequest.BookRent();
        bookRent.setBookId(book.getId());
        bookRent.setBookStatus(BookStatus.AVAILABLE);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookLedgerService.returnBook(book.getId(), bookRent));
    }
}