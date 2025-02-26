package com.test.bookledger.domain.emnum;

import com.test.global.model.enumtype.YN;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 책 대여 상태
 */
@RequiredArgsConstructor
@Getter
public enum BookStatus {
    AVAILABLE("대여가능", YN.Y),
    DAMAGED("훼손", YN.Y),
    LOST("분실", YN.N),
    RENTED("대여중", YN.N);
    private final String name;
    private final YN isReturn;
}
