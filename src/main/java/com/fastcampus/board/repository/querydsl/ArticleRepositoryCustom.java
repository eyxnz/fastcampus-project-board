package com.fastcampus.board.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<String> findAllDistinctHashtags(); // 리턴 값 (String) 이 도메인이 아니기 때문에 querydsl 을 사용함
}
