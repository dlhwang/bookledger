package com.test.bookledger.application.service;

import com.test.bookledger.application.dto.AuthorResponse;
import com.test.bookledger.domain.model.Author;
import com.test.bookledger.domain.model.AuthorVO;
import com.test.bookledger.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponse getAll(){
        List<Author> authorList = authorRepository.findAll();
        return AuthorResponse.to(authorList.stream().map(AuthorVO::to).toList());
    }

}
