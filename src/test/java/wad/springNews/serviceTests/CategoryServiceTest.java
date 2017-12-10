package wad.springNews.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.service.CategoryService;
import wad.service.NewsService;
import wad.springNews.MultipartFileMock;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private News news;
    private Long newsId;
    @Autowired
    private CategoryService categoryService;

    private final String testCategoryInput = "category1, category2";
    private List<String> listcategory;

    @Before
    @Transactional
    public void setUp() {
        News news = new News("heading", "lead", "text", LocalDateTime.now());
        newsRepository.save(news);
        newsId = newsRepository.findByHeading("heading").getId();
        this.listcategory = new ArrayList<>();
        listcategory.add("testcategory1");
    }

    @Test
    @Transactional
    public void parseCategoryListTest() {
        List<String> categories = categoryService.parseCategoryList(testCategoryInput);
        assertEquals(2, categories.size());
    }

}