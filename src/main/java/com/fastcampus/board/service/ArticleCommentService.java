package com.fastcampus.board.service;

import com.fastcampus.board.dto.ArticleCommentDto;
import com.fastcampus.board.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;

    // 댓글 리스트
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    // 댓글 저장
    public void saveArticleComment(ArticleCommentDto dto) {
    }

    // 댓글 수정
    public void updateArticleComment(ArticleCommentDto dto) {
    }

    // 댓글 삭제
    public void deleteArticleComment(Long articleCommentId) {
    }
}