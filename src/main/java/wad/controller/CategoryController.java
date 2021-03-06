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


import javax.transaction.Transactional;
import java.util.List;

@Transactional
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


    @GetMapping("/news/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @Transactional
    @GetMapping("/news/categories/{id}")
    public String showSingleCategory(@PathVariable Long id, Model model) {
        String categoryName = categoryRepository.getOne(id).getName();
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("newsMap", categoryService.getNewsRelatedToCateory(id));
        return "singleCategory";
    }

}
