package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.PostLike;
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
public class PostLikeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "PostLIke";

    private static final RowMapper<Timeline> TIMELINE_ROW_MAPPER_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline.builder()
        .id(resultSet.getLong("id"))
        .memberId(resultSet.getLong("memberId"))
        .postId(resultSet.getLong("postId"))
        .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
        .build();

    public Long getCount(Long postId) {
        var sql = String.format("""
            SELECT COUNT(id) AS count
            FROM %s
            WHERE postId = :postId
            """, TABLE);
        SqlParameterSource params = new MapSqlParameterSource().addValue("postId", postId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public PostLike save(PostLike postLike) {
        if (postLike.getId() == null) {
            return insert(postLike);
        }
        throw new UnsupportedOperationException("postLike update id not supported");
    }

    private PostLike insert(PostLike postLike) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
            .withTableName(TABLE)
            .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike.builder()
            .id(id)
            .memberId(postLike.getMemberId())
            .postId(postLike.getPostId())
            .createdAt(postLike.getCreatedAt())
            .build();
    }


}
