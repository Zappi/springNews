package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
