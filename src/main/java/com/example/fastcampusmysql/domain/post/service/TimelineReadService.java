package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimelineReadService {

    private final TimelineRepository timelineRepository;

    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        var timelines = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(timelines);
        return new PageCursor<>(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineRepository.findALlByMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return timelineRepository.findALlByMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
    }

    private static long getNextKey(List<Timeline> posts) {
        return posts.stream()
            .mapToLong(Timeline::getId)
            .min()
            .orElse(CursorRequest.NONE_KEY);
    }

}
