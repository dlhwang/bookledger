package com.test.bookledger.application.service;

import com.test.bookledger.application.dto.AuthorResponse;
import com.test.bookledger.application.dto.CategoryResponse;
import com.test.bookledger.domain.model.Author;
import com.test.bookledger.domain.model.AuthorVO;
import com.test.bookledger.domain.model.Category;
import com.test.bookledger.domain.model.CategoryVO;
import com.test.bookledger.domain.repository.AuthorRepository;
import com.test.bookledger.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse getAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return CategoryResponse.to(categoryList.stream().map(CategoryVO::to).toList());
    }

}
