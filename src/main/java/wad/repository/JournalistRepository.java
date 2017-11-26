package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Journalist;

public interface JournalistRepository extends JpaRepository<Journalist, Long> {
    Journalist findByName(String name);
}
