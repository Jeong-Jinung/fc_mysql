package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    /**
     * 대용량 데이터라면 @Transcational 고민해 봐야 한다.
     * 쓰기 시간이 너무 길어질 수 있다. or lock을 너무 오래 잡아둘 수 있다.
     * 외부 요인에 의해 transaction이 길어지는 것도 고려해야 한다.
     */
    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);
        var followerMemberIds = followReadService.getFollowers(postCommand.memberId())
            .stream()
            .map(Follow::getFromMemberId)
            .toList();

        timelineWriteService.deliveryToTimeline(postId, followerMemberIds);
       return postId;
    }

}
