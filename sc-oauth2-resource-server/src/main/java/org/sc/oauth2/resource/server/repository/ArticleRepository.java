package org.sc.oauth2.resource.server.repository;

import org.sc.oauth2.resource.server.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{

}
