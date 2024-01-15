package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {

    private final TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        var timeLines = toMemberIds.stream()
                .map(memberId -> toTimeline(postId, memberId)).toList();
            timelineRepository.bulkInsert(timeLines);
    }

    private static Timeline toTimeline(Long postId, Long memberId) {
        return Timeline.builder()
            .memberId(memberId)
            .postId(postId)
            .build();
    }

}
