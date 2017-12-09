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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            Category existingCategory = categoryRepository.findByName(categoryName.toLowerCase());

            if (existingCategory != null) {
                existingCategory.getNewsList().add(news);
                news.getCategoryList().add(existingCategory);
                newsRepository.save(news);
                categoryRepository.save(existingCategory);
            } else {

                Category category = new Category(categoryName.toLowerCase());
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

    public Map<Long, String> getNewsRelatedToCateory(Long categoryId) {
        List<News> news = categoryRepository.getOne(categoryId).getNewsList();
        HashMap<Long, String> allNews = new HashMap<>();
        for (News foundNews : news) {
          allNews.put(foundNews.getId(), foundNews.getHeading());
        }

        return allNews;
    }

    public void deleteCategoryRelationToNews(String newsHeading) {
        List<Category> allCategories = categoryRepository.findAll();
        for (Category category: allCategories) {
            for (News news: category.getNewsList()) {
                if(news.getHeading().equals(newsHeading)) {
                    category.getNewsList().remove(news);
                    if(category.getNewsList().size() == 1) {
                        categoryRepository.deleteById(category.getId());
                    }
                }
            }
        }
    }
}
