package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostWriteService {

    private final PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post.builder()
            .memberId(command.memberId())
            .contents(command.contents())
            .build();
        return postRepository.save(post).getId();
    }

    @Transactional
    public void likePost(Long postId) {
        var post = postRepository.findById(postId, true).orElseThrow(); // 데이터조회
        post.incrementLikeCount(); // 데이터변경
        postRepository.save(post); // 데이터저장
    }

    public void likePostByOptimisticLock(Long postId) {
        var post = postRepository.findById(postId, false).orElseThrow(); // 데이터조회
        post.incrementLikeCount(); // 데이터변경
        postRepository.save(post); // 데이터저장
    }

}
