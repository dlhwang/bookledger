package com.test.global.model.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YN {
    Y("O", "예"), N("X", "아니오");

    private final String title;
    private final String title2;

    public static boolean checked(YN yn) {
        return yn == Y;
    }
}
