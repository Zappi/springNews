package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.News;
import wad.repository.ImageRepository;
import wad.repository.NewsRepository;

import java.io.IOException;
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private NewsRepository newsRepository;

    public void addNewsToImage(Long id, MultipartFile image) throws IOException {

        Image newImage = new Image();
        newImage.setContent(image.getBytes());
        News news = newsRepository.getOne(id);

        imageRepository.save(newImage).setNews(news);
        newsRepository.getOne(id).setImage(newImage);

    }

}
