package com.egg.egglectricity.repositories;

import com.egg.egglectricity.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query("SELECT COALESCE(MAX(a.number), 0) FROM Article a")
    int findMaxArticleNumber();
}
