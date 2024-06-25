package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.article.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>
{

}
