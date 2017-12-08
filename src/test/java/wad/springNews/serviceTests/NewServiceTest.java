package wad.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.News;
import wad.repository.NewsRepository;
import wad.service.NewsService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class NewServiceTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsService newsService;

    private Long testId;
    private News testNews;

    @Before
    @Transactional
    public void setUp()  {
        News news = new News("Finland won football game", "Finland won 9-1", "Can you belive it?", LocalDateTime.now());
        newsRepository.save(news);

        testNews = newsRepository.findByHeading("Finland won football game");
        testId = testNews.getId();
    }

    @Test
    @Transactional
    public void handleEditNews()  {
        newsService.handleEdit(testId, "Finland won football game", "Finland won 7-1", "Can you belive it?");
        assertEquals("Finland won 7-1", newsRepository.getOne(testId).getLead());
    }
}
