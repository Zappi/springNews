package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.News;
import wad.repository.ImageRepository;
import wad.repository.NewsRepository;
import wad.service.CategoryService;
import wad.service.ImageService;
import wad.service.JournalistService;
import wad.service.NewsService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JournalistService journalistService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private NewsService newsService;


    //Maps the frontpage and shows the 5 newest articles
    @GetMapping("/")
    public String showFrontPage(Model model) {

        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC, "localTime");

        model.addAttribute("news", newsRepository.findAll(pageable));


        List<Long> imageids = new ArrayList<>();
        for (News news: newsRepository.findAll(pageable)) {
            imageids.add(news.getImage().getId());
        }
        model.addAttribute("imageids", imageids);
        return "index";
    }


    //Shows all the news
    @GetMapping("/news")
    public String listAllNews(Model model) {
        if(newsRepository.findAll().size() > 0) {
            Pageable pageable = PageRequest.of(0, newsRepository.findAll().size(), Sort.Direction.DESC, "localTime");
            model.addAttribute("news", newsRepository.findAll(pageable));
        }
        return "allNews";
    }

    //Shows a single news article
    @GetMapping("/news/{id}")
    public String viewSingleNews(Model model, @PathVariable Long id) {
        News news = newsRepository.getOne(id);

        news.incrementPageOpened();
        model.addAttribute("news", news);
        model.addAttribute("categories", categoryService.getCategories(news.getCategoryList()));
        model.addAttribute("journalists", journalistService.getJournalists(news.getJournalistList()));
        model.addAttribute("imageid", news.getImage().getId());
        return "news";
    }



    @GetMapping(path="/news/{id}/content", produces="image/png")
    @ResponseBody
    @Transactional
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image fo = imageRepository.getOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
        headers.setContentLength(fo.getContentLength());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }


    @Transactional
    @PostMapping("/news/delete/{id}")
    public String removeSingleNews(@PathVariable Long id) {
        News news = newsRepository.getOne(id);
        categoryService.deleteCategoryRelationToNews(news.getHeading());
        journalistService.deleteJournalistRelationToNews(news.getHeading());
        imageRepository.deleteById(news.getImage().getId());
        newsRepository.deleteById(id);
        return "redirect:/news";
    }

    @GetMapping("/news/create")
        public String createNewPieceOfNews() {
        return "createnew";
    }

    @Transactional
    @PostMapping("/news/create")
    public String submitNewPieceOfNews(@RequestParam String heading,
                                        @RequestParam String lead, @RequestParam String text,
                                        @RequestParam String journalists, @RequestParam String categories,
                                        @RequestParam("file") MultipartFile image, Model model) throws IOException {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<String> errors = newsService.validate(heading,lead,text,journalists,categories,image);
        if(errors.size() > 0) {

            model.addAttribute("errors" , errors);
            return "createnew";
        }


        Long newsId = newsRepository.save(new News(heading, lead, text, localDateTime)).getId();

        imageService.addNewsToImage(newsId, image);
        categoryService.addNewsToCategories(newsId, categoryService.parseCategoryList(categories));
        journalistService.addNewsToJournalists(newsId, journalistService.parseJournalistList(journalists));
        model.addAttribute("success", "Created successfully!");

        return "redirect:/news/create";
    }

    @Transactional
    @GetMapping("/news/edit/{id}")
    public String editPieceOfNews(@PathVariable Long id, Model model) {

        model.addAttribute("news", newsService.handleEdit(newsRepository.getOne(id)));
        model.addAttribute("newsid", id);
        return "edit";
    }

    @Transactional
    @PostMapping("/news/edit/{id}")
    public String editPieceOfNews(@RequestParam String heading,
                @RequestParam String lead, @RequestParam String text,
                @RequestParam String journalists, @RequestParam String categories,
                @RequestParam("file") MultipartFile image,
                @PathVariable Long id, Model model) throws IOException {

        List<String> errors = newsService.validate(heading,lead,text,journalists,categories,image);
        if(errors.size() > 0) {

            model.addAttribute("errors" , errors);
            return "edit";
        }


        imageService.addNewsToImage(id, image);
        newsService.handleEdit(id, heading, lead, text);
        categoryService.addNewsToCategories(id, categoryService.parseCategoryList(categories));
        journalistService.addNewsToJournalists(id, journalistService.parseJournalistList(journalists));

        return "redirect:/news";
    }

    //Shows now only most clicked of all the time
    @GetMapping("/news/trending")
    public String getLastWeeksMostReadNews(Model model) {
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "pageOpened");
        model.addAttribute("news", newsRepository.findAll(pageable));

        return "trending";
    }
}
