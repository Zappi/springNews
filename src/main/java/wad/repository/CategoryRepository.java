package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.domain.Category;
import wad.domain.News;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);


    //@Query("SELECT Category.name FROM Category, NEWS, NEWS_CATEGORY_LIST WHERE NEWS_LIST_ID = :id AND Category.id = news_category_list.category_list_id")
    //List<Category> findSingleNewsCateogries(Long id);
}
