package com.test.bookledger.application.dto;

import com.test.bookledger.domain.emnum.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class BookRequest {

    @Data
    @Schema(description = "도서 검색 조건")
    public static class BookSearch{
        @Schema(description = "카테고리 아이디")
        String categoryId;
        @Schema(description = "카테고리명", example = "문학")
        String categoryName;
        @Schema(description = "지은이 아이디")
        String authorId;
        @Schema(description = "지은이", example = "권태영")
        String authorName;
        @Schema(description = "도서명", example = "게으른 사랑")
        String bookName;

        public com.test.bookledger.domain.model.BookSearch to(){
            return com.test.bookledger.domain.model.BookSearch.to(this);
        }
    }

    @Data
    @Schema(description = "도서 신규 저장")
    public static class BookSave{
        @Schema(description = "도서명", example = "-1년차 게임 개발", requiredMode = Schema.RequiredMode.REQUIRED)
        String bookName;
        @Schema(description = "카테고리 아이디 배열", requiredMode = Schema.RequiredMode.REQUIRED)
        List<String> categoryIds;
        @Schema(description = "지은지 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        String authorId;

        public com.test.bookledger.domain.model.BookSave to(){
            return new com.test.bookledger.domain.model.BookSave(this.bookName, this.categoryIds, this.authorId);
        }
    }

    @Data
    @Schema(description = "도서 수정")
    public static class BookModify{
        @Schema(description = "도서아이디", example = "-LsOI9yiLrfNjVGuFGGYbG", requiredMode = Schema.RequiredMode.REQUIRED)
        String bookId;
        @Schema(description = "도서명", example = "게으른 사랑", requiredMode = Schema.RequiredMode.REQUIRED)
        String bookName;
        @Schema(description = "카테고리 아이디 배열")
        List<String> categoryIds;
        @Schema(description = "지은지 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        String authorId;

        public com.test.bookledger.domain.model.BookModify to(){
            return new com.test.bookledger.domain.model.BookModify(bookId, bookName, categoryIds, authorId);
        }
    }

    @Data
    @Schema(description = "도서 대여 및 반납시 사용 ")
    public static class BookRent{
        @Schema(description = "도서아이디", example = "-LsOI9yiLrfNjVGuFGGYbG", requiredMode = Schema.RequiredMode.REQUIRED)
        String bookId;
        @Schema(description = "도서 상태", example = "AVAILABLE", requiredMode = Schema.RequiredMode.REQUIRED)
        BookStatus bookStatus;
    }
}
