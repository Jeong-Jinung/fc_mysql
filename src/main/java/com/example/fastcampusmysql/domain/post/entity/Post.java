package com.example.fastcampusmysql.domain.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private final Long memberId;
    private final String contents;
    private final LocalDate createdDate;
    private Long likeCount;
    private Long version;
    private final LocalDateTime createdAt;

    @Builder
    private Post(Long id, Long memberId, String contents, LocalDate createdDate, Long likeCount, Long version, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.likeCount = likeCount == null ? 0L : likeCount;
        this.version = version == null ? 0L : version;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;

    }

    public void incrementLikeCount() {
        likeCount += 1;
    }
}
