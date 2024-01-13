package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberNicknameHistory {
    final private Long id;
    final private Long memberId;
    /*
        데이터의 최신성을 항상 고려해야 하는가를 고려해야 한다. -> 과거의 기록을 보여줄 필요가 있을지도 고민
        정규화/비정규화 대상을 고려해야 한다.
     */
    final private String nickname;
    final private LocalDateTime createdAt;


    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
