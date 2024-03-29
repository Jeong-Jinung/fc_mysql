package com.example.fastcampusmysql.util;

import io.swagger.v3.oas.models.security.SecurityScheme.In;

public record CursorRequest(
    Long key,
    int size
) {

    public static final Long NONE_KEY = -1L;

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }

    public Boolean hasKey() {
        return key != null;
    }

}
