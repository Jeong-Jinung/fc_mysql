package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimeLinePostsUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> excute(Long memberId, CursorRequest cursorRequest) {
        var followers = followReadService.getFollowings(memberId);
        var followerMemberIds = followers.stream()
            .map(Follow::getToMemberId).toList();

        return postReadService.getPosts(followerMemberIds, cursorRequest);
    }

    public PageCursor<Post> excuteByTimeline(Long memberId, CursorRequest cursorRequest) {
        var timelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = timelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);

        return new PageCursor<>(timelines.nextCursorRequest(), posts);
    }

}
