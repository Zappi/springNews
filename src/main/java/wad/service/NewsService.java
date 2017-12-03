package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wad.domain.Image;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public void handleEdit(Long id, String heading, String lead, String text) {
        News edited = newsRepository.getOne(id);
        edited.setHeading(heading);
        edited.setLead(lead);
        edited.setText(text);

        newsRepository.getOne(id).getCategoryList().clear();
        newsRepository.getOne(id).getJournalistList().clear();


        newsRepository.save(edited);
    }
}
