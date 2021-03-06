package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.News;
import wad.repository.ImageRepository;
import wad.repository.NewsRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

@Transactional
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Transactional
    public void addNewsToImage(Long id, MultipartFile image) throws IOException {

        Image newImage = new Image(LocalDateTime.now());
        newImage.setNews(newsRepository.getOne(id));

        try {
            newImage.setContent(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        newImage.setContentLength(image.getSize());
        newImage.setContentType(image.getContentType());
        newImage.setName(image.getName());

        News news = newsRepository.getOne(id);

        imageRepository.save(newImage).setNews(news);
        newsRepository.getOne(id).setImage(newImage);
    }

}
