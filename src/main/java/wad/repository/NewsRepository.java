package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
