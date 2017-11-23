package wad.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.News;
import wad.repository.NewsRepository;
import java.time.LocalDate;


@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;


    @GetMapping("/news/{id}")
    public String viewSingleNews(Model model, @PathVariable Long id) {
        model.addAttribute("news", newsRepository.getOne(id));
        return "news";
    }


    @GetMapping("/news/create")
        public String createNewPieceOfNews() {
        return "createnew";
    }

    @PostMapping("/news/create")
    public String submiteNewPieceOfNews(@RequestParam String heading,
                                        @RequestParam String lead, @RequestParam String text,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseTime) {
        newsRepository.save(new News(heading, lead, text, releaseTime));

        return "redirect:/news/create";
    }
}
