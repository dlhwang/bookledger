package com.test.bookledger.domain.repository;

import com.test.bookledger.domain.emnum.BookStatus;
import com.test.bookledger.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, BookRepositoryCustom {
    List<Book> findAllByBookStatus(BookStatus bookStatus);
}
