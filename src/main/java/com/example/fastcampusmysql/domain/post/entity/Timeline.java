package com.example.fastcampusmysql.domain.post.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Timeline {

    private final Long id;

    private final Long memberId;

    private final Long postId;

    private final LocalDateTime createdAt;

    @Builder
    private Timeline(Long id, Long memberId, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.postId = Objects.requireNonNull(postId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
