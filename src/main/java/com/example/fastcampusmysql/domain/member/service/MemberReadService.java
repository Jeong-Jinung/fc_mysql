package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    final private MemberRepository memberRepository;

    public MemberDto getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return toDto(member);
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(
            member.getId(),
            member.getEmail(),
            member.getNickname(),
            member.getBirthday()
        );
    }



}
