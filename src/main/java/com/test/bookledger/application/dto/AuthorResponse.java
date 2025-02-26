package com.test.bookledger.application.dto;

import com.test.bookledger.domain.model.AuthorVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "지은이 정보")
public class AuthorResponse {

    List<AuthorList> authorList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PROTECTED)
    @Schema(description = "지은이 목록")
    public static class AuthorList{
        @Schema(description = "지은이 아이디", example = "V1StGXR8_Z5jdHi6B-myT")
        String authorId;
        @Schema(description = "지은이", example = "권태영")
        String name;

        public static AuthorList to(AuthorVO vo){
            return AuthorList.builder()
                    .authorId(vo.getId())
                    .name(vo.getName())
                    .build();
        }
    }

    public static AuthorResponse to(List<AuthorVO> vos){
        return AuthorResponse.builder()
                .authorList(vos.stream().map(AuthorList::to).toList())
                .build();
    }
}
