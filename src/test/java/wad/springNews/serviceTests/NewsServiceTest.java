package wad.springNews.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.News;
import wad.repository.NewsRepository;
import wad.service.NewsService;
import wad.springNews.MultipartFileMock;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsService newsService;

    private Long testId;
    private News testNews;
    private MultipartFileMock img;
    private MultipartFileMock jpgimg;

    @Before
    @Transactional
    public void setUp()  {
        News news = new News("Finland won football game", "Finland won 9-1", "Can you belive it?", LocalDateTime.now());
        newsRepository.save(news);

        testNews = newsRepository.findByHeading("Finland won football game");
        testId = testNews.getId();
        this.img = new MultipartFileMock("Image", "Image.png", "image/png", new Long(120), new byte[0]);
        this.jpgimg = new MultipartFileMock("Image", "Image.jpg", "image/jpg", new Long(120), new byte[0]);
    }

    @Test
    @Transactional
    public void handleEditNews()  {
        newsService.handleEdit(testId, "Finland won football game", "Finland won 7-1", "Can you belive it?");
        assertEquals("Finland won 7-1", newsRepository.getOne(testId).getLead());
    }

    @Test
    @Transactional
    public void validateNews() throws IOException {
        List<String> errors = new ArrayList<>();
        errors = newsService.validate("this", "lead", "text","jack","category",img);
        assertEquals(0, errors.size());
    }

    @Test
    @Transactional
    public void validateReturnErrors() throws IOException {
        String tooLong = "a";

        for(int i=0;i<=10001; i++) {
            tooLong+="a";
        }

        List<String> errors = newsService.validate(tooLong, tooLong, tooLong, "", "", jpgimg);
        assertNotNull(errors);

    }
}

