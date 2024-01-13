package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "MemberNicknameHistory";
    static final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet rs, int rowNum) -> MemberNicknameHistory.builder()
        .id(rs.getLong("id"))
        .memberId(rs.getLong("memberId"))
        .nickname(rs.getString("nickname"))
        .createdAt(rs.getObject("createdAt", LocalDateTime.class))
        .build();

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var sql = String.format("SELECT * FROM %s WHERE memberId = memberId", TABLE);
        var params = new MapSqlParameterSource().addValue("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }


    public MemberNicknameHistory save(MemberNicknameHistory history) {
        /*
            member id를 보고 갱신 또는 삽입을 정함
            반환값은 id를 담아서 반환한다.
         */
        if (history.getId() == null) {
            return insert(history);
        }
        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");
    }

    private MemberNicknameHistory insert(MemberNicknameHistory member) {
        /*
            insert 쿼리를 날린다.
            반환값은 id를 담아서 반환한다.
         */
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
            .withTableName(TABLE)
            .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return MemberNicknameHistory.builder()
            .id(id)
            .memberId(member.getMemberId())
            .nickname(member.getNickname())
            .createdAt(member.getCreatedAt())
            .build();
    }

}
