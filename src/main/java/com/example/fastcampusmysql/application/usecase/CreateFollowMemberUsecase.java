package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 클래스명에서 명확하게 usecase 이름을 정희한다.
 */
@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {

    /*
        회원에 대한 쓰기 권한이 절대 없다.
     */
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 MemberId를 가지고 회원 정보를 가져온다.
            2. FollowWriteService.create() 호출
         */
        var formMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(formMember, toMember);
    }

}
