package wad.springNews.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Journalist;
import wad.domain.News;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;
import wad.service.JournalistService;
import wad.service.NewsService;
import wad.springNews.MultipartFileMock;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JournalistServiceTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsService newsService;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private JournalistService journalistService;

    private Long testNewsId;
    private News testNews;
    private Journalist journalist;
    private Journalist journalist2;
    private String journalistString;
    private List<String> journalistList;

    private List<Journalist> listOfJournalistType;

    @Before
    @Transactional
    public void setUp() {
        testNews = new News("Finland won a football game", "Finland won 9-1", "Can you belive it?", LocalDateTime.now());
        newsRepository.save(testNews);
        testNewsId = newsRepository.findByHeading("Finland won a football game").getId();
        this.journalist = new Journalist("Mike");
        this.journalist2 = new Journalist("Bob");
        this.journalistList = new ArrayList<>();
        this.journalistList.add("Mike");
        this.journalistString = "Mike, Bob";

        this.listOfJournalistType = new ArrayList<>();
        listOfJournalistType.add(journalist);
        listOfJournalistType.add(journalist2);
        journalistRepository.save(journalist);
        journalistRepository.save(journalist2);
    }

    @Test
    public void parseJournalistList() {
        List<String> journalists = journalistService.parseJournalistList(journalistString);
        assertEquals(2, journalists.size());
    }






}