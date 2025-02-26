package com.test.bookledger.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class BookEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = -6778628839085803792L;
    private final String id;
}
