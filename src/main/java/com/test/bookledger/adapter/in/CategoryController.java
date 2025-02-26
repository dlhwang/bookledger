package com.test.bookledger.adapter.in;

import com.test.bookledger.application.dto.AuthorResponse;
import com.test.bookledger.application.dto.CategoryResponse;
import com.test.bookledger.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "카테고리 v1", description = "카테고리 관련 컨트롤러 입니다.")
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    @Operation(summary = "카테고리 조회", description = "카테고리 관련 전체 리스트 조회입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<CategoryResponse> getCategoryAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
