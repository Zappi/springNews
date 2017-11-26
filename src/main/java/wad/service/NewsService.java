package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private CategoryRepository categoryRepository;

}
