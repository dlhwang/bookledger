package com.test.bookledger.application.dto;

import com.test.bookledger.domain.model.AuthorVO;
import com.test.bookledger.domain.model.CategoryVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "카테고리 정보")
public class CategoryResponse {

    List<CategoryList> categoryList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PROTECTED)
    @Schema(description = "카테고리 목록")
    public static class CategoryList{
        @Schema(description = "카테고리 아이디", example = "V1StGXR8_Z5jdHi6B-myT")
        String authorId;
        @Schema(description = "카테고리명", example = "문학")
        String name;

        public static CategoryList to(CategoryVO vo){
            return CategoryList.builder()
                    .authorId(vo.getId())
                    .name(vo.getName())
                    .build();
        }
    }

    public static CategoryResponse to(List<CategoryVO> vos){
        return CategoryResponse.builder()
                .categoryList(vos.stream().map(CategoryList::to).toList())
                .build();
    }
}
