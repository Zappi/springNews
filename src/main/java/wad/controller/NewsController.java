package wad.controller;


import com.sun.org.apache.regexp.internal.RE;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Category;
import wad.domain.Image;
import wad.domain.Journalist;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.ImageRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;
import wad.service.CategoryService;
import wad.service.ImageService;
import wad.service.JournalistService;
import wad.service.NewsService;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JournalistService journalistService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;

    private Category category;
    private Journalist journalist;


    @GetMapping("/news")
    public String listAllNews(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "allNews";
    }

    @GetMapping("/news/{id}")
    public String viewSingleNews(Model model, @PathVariable Long id) {
        News news = newsRepository.getOne(id);
        news.incrementPageOpened();
        model.addAttribute("news", news);
        model.addAttribute("categories", categoryService.getCategories(news.getCategoryList()));
        model.addAttribute("journalists", journalistService.getJournalists(news.getJournalistList()));
        model.addAttribute("imageid", news.getImage().getId());
        System.out.println(news.getImage().getId());
        return "news";
    }

    @GetMapping(path= "news/{id}/content", produces="image/png")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageRepository.getOne(id).getContent();
    }


    @GetMapping("/news/create")
        public String createNewPieceOfNews() {
        return "createnew";
    }

    //Fix this
    @PostMapping("/news/create")
    public String submitNewPieceOfNews(@RequestParam String heading,
                                        @RequestParam String lead, @RequestParam String text,
                                        @RequestParam String journalists, @RequestParam String categories,
                                        @RequestParam("file") MultipartFile image) throws IOException {

        LocalDate localDate = LocalDate.now();

        Long newsId = newsRepository.save(new News(heading, lead, text, localDate)).getId();

        imageService.addNewsToImage(newsId, image);
        categoryService.addNewsToCategories(newsId, categoryService.parseCategoryList(categories));
        journalistService.addNewsToJournalists(newsId, journalistService.parseJournalistList(journalists));



        return "redirect:/news/create";
    }
}
