package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public List<News> getAllNewsInfo() {
        List<News> news = new ArrayList<>();
        for (News singleNew: newsRepository.findAll()
             ) {
            news.add(singleNew);
        }
        return news;
    }


}
