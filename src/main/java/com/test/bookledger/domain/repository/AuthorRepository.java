package com.test.bookledger.domain.repository;

import com.test.bookledger.domain.model.Author;
import com.test.bookledger.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
}
