package com.fastcampus.board.service;

import com.fastcampus.board.domain.type.SearchType;
import com.fastcampus.board.dto.ArticleDto;
import com.fastcampus.board.dto.ArticleUpdateDto;
import com.fastcampus.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 게시글 검색
    // 페이지네이션을 이용하기 위해서 Page 사용
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();
    }

    // 게시글 상세 페이지
    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long articleId) {
        return null;
    }

    // 게시글 생성
    public void saveArticle(ArticleDto dto) {
    }

    // 게시글 수정
    public void updateArticle(long articleId, ArticleUpdateDto dto) {
    }

    // 게시글 삭제
    public void deleteArticle(long articleId) {
    }
}
