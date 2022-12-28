package com.fastcampus.board.repository.querydsl;

import com.fastcampus.board.domain.Article;
import com.fastcampus.board.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {
    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct() // 해시태그가 중복되더라도 하나만 출력
                .select(article.hashtag) // 도메인의 하나의 컬럼만 출력
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}
