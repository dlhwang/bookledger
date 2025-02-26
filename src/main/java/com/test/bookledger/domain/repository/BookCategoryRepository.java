package com.test.bookledger.domain.repository;

import com.test.bookledger.domain.model.Book;
import com.test.bookledger.domain.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, String> {

}
