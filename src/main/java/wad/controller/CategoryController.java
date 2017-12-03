package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wad.domain.Category;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.service.CategoryService;
import wad.service.NewsService;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("categories/{id}")
    public String showSingleCategory(@PathVariable Long id, Model model) {
        String categoryName = categoryRepository.getOne(id).getName();
        List<News> news = categoryRepository.getOne(id).getNewsList();
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("news", news);

        return "singleCategory";
    }

}
