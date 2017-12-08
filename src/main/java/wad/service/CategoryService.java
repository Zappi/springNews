package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Category;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CategoryService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryRepository categoryRepository;



    public List<String> parseCategoryList(String category) {
        List<String> listOfCategories = new ArrayList<>();
        int i = 0;
        String categoryForList ="";

        for (int x = 0; x < category.length(); x++) {

            if(category.charAt(x) == ',') {
                listOfCategories.add(categoryForList);
                categoryForList = "";

            } else {
                if(category.charAt(x) == ' ') {
                    continue;

                }
                categoryForList += category.charAt(x);
            }
        }

        //Adding the last category also for the list
        listOfCategories.add(categoryForList);


        return listOfCategories;
    }

    //Creates new cateories if they don't exist and adds to them in a single new.
    //@Param1 is a single piece of news Id
    //@Param2 is list of category names
    public void addNewsToCategories(Long newsId, List<String> categories) {
        News news = newsRepository.getOne(newsId);

        for (String categoryName : categories) {

            Category existingCategory = categoryRepository.findByName(categoryName);

            if (existingCategory != null) {
                existingCategory.getNewsList().add(news);
                news.getCategoryList().add(existingCategory);
                newsRepository.save(news);
                categoryRepository.save(existingCategory);
            } else {

                Category category = new Category(categoryName);
                category.getNewsList().add(news);
                categoryRepository.save(category);

                news.getCategoryList().add(category);
                newsRepository.save(news);
            }
        }
    }

    public List<Category> getCategories(List<Category> categoryList) {
        List<Category> categories = new ArrayList<>();
        for (Category category: categoryList){
            categories.add(categoryRepository.getOne(category.getId()));

        }
        return categories;
    }

    public List<Long> getNewsRelatedToCateory(Long categoryId) {
        List<News> news = categoryRepository.getOne(categoryId).getNewsList();
        List<Long> id = new ArrayList<>();
        for (News newsId: news) {
            id.add(newsId.getId());
        }

        return id;
    }
}
