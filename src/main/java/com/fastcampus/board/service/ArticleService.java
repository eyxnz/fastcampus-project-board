package com.fastcampus.board.service;

import com.fastcampus.board.domain.Article;
import com.fastcampus.board.domain.UserAccount;
import com.fastcampus.board.domain.constant.SearchType;
import com.fastcampus.board.dto.ArticleDto;
import com.fastcampus.board.dto.ArticleWithCommentsDto;
import com.fastcampus.board.repository.ArticleRepository;
import com.fastcampus.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    // 게시글 검색
    // 페이지네이션을 이용하기 위해서 Page 사용
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        // 검색어가 없을 경우
        // isBlank() : 빈 문자열이거나 스페이스로 이루어진 문자열이거나
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        // 검색어가 있을 경우
        // searchType 에 따라 다른 쿼리를 내보냄
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    // 게시글 상세 페이지 - 댓글 포함
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        // .orElseThrow : optional 을 까주는데 예외 처리도 해줌
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    // 게시글 상세 페이지 - 댓글 미포함
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    // 게시글 생성
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    // 게시글 수정
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            // getReferenceById : findById 와 다르게 select 쿼리를 날리지 않음
            Article article = articleRepository.getReferenceById(dto.id());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            // 게시글의 사용자와 인증된 사용자가 동일하다면 업데이트를 수행
            if (article.getUserAccount().equals(userAccount)) {
                // title, content 는 notnull field 이고 hashtag 는 null 가능
                if (dto.title() != null) {
                    article.setTitle(dto.title());
                }
                if (dto.content() != null) {
                    article.setContent(dto.content());
                }
                article.setHashtag(dto.hashtag());
            }

            // 클래스 레벨 트랜잭션에 의해 method 단위로 트랜잭션이 묶여있음
            // 트랜잭션이 끝날 때, 영속성 컨텍스트는 article 의 변화를 감지함
            // articleRepository.save(article);
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    // 게시글 삭제
    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    // 총 게시글 개수 리턴
    public long getArticleCount() {
        return articleRepository.count();
    }

    // 해시태그를 사용해서 게시글 검색
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    // 해시태그 리스트 리턴
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
