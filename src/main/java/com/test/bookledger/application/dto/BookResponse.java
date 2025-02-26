package com.test.bookledger.application.dto;

import com.test.bookledger.domain.emnum.BookStatus;
import com.test.bookledger.domain.model.BookVO;
import com.test.global.model.enumtype.YN;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "도서 정보")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookResponse {

    Page<BookVO> bookList;

    @Schema(description = "도서 상세 정보")
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    public static class BookVO {
        @Schema(description = "도서 아이디", example = "hbavArRhRwOfQ5swS7eAq")
        String bookId;
        @Schema(description = "도서명", example = "-1년차 게임 개발")
        String bookName;
        @Schema(description = "카테고리", example = "[문학, 경제경영]")
        String categoryName;
        @Schema(description = "지은이 정보")
        AuthorResponse.AuthorList author;
        YN deleteYN;
        @Schema(description = "도서 상태")
        BookStatus bookStatus;

        public static BookVO to(com.test.bookledger.domain.model.BookVO vo){
            return BookVO.builder()
                    .bookId(vo.getBookId())
                    .bookName(vo.getBookName())
                    .categoryName(vo.getCategoryName())
                    .author(AuthorResponse.AuthorList.to(vo.getAuthor()))
                    .bookStatus(vo.getBookStatus())
                    .deleteYN(vo.getDeleteYN())
                    .build();
        }
    }

    public static BookResponse to(Page<com.test.bookledger.domain.model.BookVO> vos){
        return BookResponse.builder()
                .bookList(new PageImpl<>(vos.stream().map(BookVO::to).toList(), vos.getPageable(), vos.getTotalElements()))
                .build();
    }
}
