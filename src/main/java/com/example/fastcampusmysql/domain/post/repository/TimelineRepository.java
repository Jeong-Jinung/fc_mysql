package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
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

@Repository
@RequiredArgsConstructor
public class TimelineRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "Timeline";

    private static final RowMapper<Timeline> TIMELINE_ROW_MAPPER_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline.builder()
        .id(resultSet.getLong("id"))
        .memberId(resultSet.getLong("memberId"))
        .postId(resultSet.getLong("postId"))
        .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
        .build();

    public List<Timeline> findALlByMemberIdAndOrderByIdDesc(Long id, Long memberId, int size) {
        var sql =
            """
            /*TimelineRepoitory.findALlByMemberIdAndOrderByIdDesc*/
            SELECT *
            FROM %s
            WHERE memberId = :memberId
            ORDER BY id DESC
            LIMIT :size
            """.formatted(TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("memberId", memberId)
            .addValue("size", size);
        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER_ROW_MAPPER);
    }

    public List<Timeline> findAllByInMemberIdAndOrderByIdDesc(Long id, List<Long> memberIds, int size) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        var sql = String.format(
            """
            SELECT *
            FROM %s
            WHERE memberId in (:memberIds) and id < :id
            ORDER BY id desc
            LIMIT :size
            """, TABLE);
        var params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("memberIds", memberIds)
            .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER_ROW_MAPPER);
    }

    public Timeline save(Timeline timeline) {
        if (timeline.getId() == null) {
            return insert(timeline);
        }
        throw new UnsupportedOperationException("timeline update id not supported");
    }


    private Timeline insert(Timeline timeline) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
            .withTableName(TABLE)
            .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(timeline);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Timeline.builder()
            .id(id)
            .memberId(timeline.getMemberId())
            .postId(timeline.getPostId())
            .createdAt(timeline.getCreatedAt())
            .build();
    }

    public void bulkInsert(List<Timeline> timelines) {
        var sql = String.format("""
            INSERT INTO %s (memberId, postId, createdAt)
            VALUES (:memberId, :postId, :createdAt)
            """, TABLE);

        SqlParameterSource[] params = timelines.stream()
            .map(BeanPropertySqlParameterSource::new)
            .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params); // JPA를 쓸때도 벌크인서트 때문에 JDBC를 쓰는 경우가 있다.
    }


}
