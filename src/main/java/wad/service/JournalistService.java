package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Category;
import wad.domain.Journalist;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class JournalistService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;

    public List<String> parseJournalistList(String journalist) {
        List<String> listOfJournalists = new ArrayList<>();
        int i = 0;
        String journalistForList ="";

        for (int x = 0; x < journalist.length(); x++) {

            if(journalist.charAt(x) == ',') {
                listOfJournalists.add(journalistForList);
                journalistForList = "";

            } else {
                journalistForList += journalist.charAt(x);
            }
        }

        //Adding the last journalist also for the list
        listOfJournalists.add(journalistForList);


        return listOfJournalists;

    }

    public void addNewsToJournalists(Long newsId, List<String> journalistList) {
        News news = newsRepository.getOne(newsId);

        for (String journalistName : journalistList) {

            Journalist existingJournalist = journalistRepository.findByName(journalistName);

            if (existingJournalist != null) {
                existingJournalist.getNewsList().add(news);
                news.getJournalistList().add(existingJournalist);
                newsRepository.save(news);
                journalistRepository.save(existingJournalist);
            } else {

                Journalist journalist = new Journalist(journalistName);
                journalist.getNewsList().add(news);
                journalistRepository.save(journalist);

                news.getJournalistList().add(journalist);
                newsRepository.save(news);
            }
        }

    }

    public List<Journalist> getJournalists(List<Journalist> journalistList) {
        List<Journalist> journalists = new ArrayList<>();
        for (Journalist journalist: journalistList){
            journalists.add(journalistRepository.getOne(journalist.getId()));

        }
        return journalists;
    }

    public void deleteJournalistRelationToNews(String newsHeading) {
        List<Journalist> allJournalists = journalistRepository.findAll();
        for (Journalist journalist: allJournalists) {
            for (News news: journalist.getNewsList()) {
                if(news.getHeading().equals(newsHeading)) {
                    journalist.getNewsList().remove(news);
                    if(journalist.getNewsList().size() == 1) {
                        journalistRepository.deleteById(journalist.getId());
                    }
                }
            }
        }
    }
}
