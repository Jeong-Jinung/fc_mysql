package com.example.fastcampusmysql.domain.follow.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/**
 * 팔로우를 당하는 사람과 팔로우를 하는 사람
 * 이름은 항상 따라올텐데 이걸 어떻게 해야할까? -> follow 테이브렝 넣어야할까? join을 해야할까??
 * 데이터의 최신성을 보장해야 할까? -> 팔로우를 하는 사람의 이름을 가져오는 것은 최신성을 보장해야 한다. -> 정규화를 해야한다.
 * 연관데이터를 어떻게 가져올 것인가? -> Join 아니면 query를 두번 날려야 한다.
 * join은 가능한 미루는게 좋다. 왜냐하면 엔티티끼리 강한 결합을 하기 때문이다. -> 나중에 성능 문제를 풀기 어려워 진다. 결합을 낮추는게 좋음
 */
@Getter
public class Follow {

    private final Long id;
    private final Long fromMemberId;
    private final Long toMemberId;
    private final LocalDateTime createdAt;

    @Builder
    private Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
