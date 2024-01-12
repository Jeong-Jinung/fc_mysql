package com.example.fastcampusmysql.domain.member.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName() {
        // given
        var member = MemberFixtureFactory.create();
        var expected = "pnu";

        // when
        member.changeNickname(expected);

        // then
        assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() {
        // given
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "pnupnupnupnu";

        // when
        // then
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> member.changeNickname(overMaxLengthName)
        );


    }



}