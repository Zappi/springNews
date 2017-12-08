package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.News;
import wad.repository.CategoryRepository;
import wad.repository.JournalistRepository;
import wad.repository.NewsRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void handleEdit(Long id, String heading, String lead, String text) {
        News edited = newsRepository.getOne(id);
        edited.setHeading(heading);
        edited.setLead(lead);
        edited.setText(text);

        newsRepository.getOne(id).getCategoryList().clear();
        newsRepository.getOne(id).getJournalistList().clear();


        newsRepository.save(edited);
    }


    public List<String> validate(String heading, String lead, String text, String journalists, String categories, MultipartFile image) throws IOException {
        List<String> errors = new ArrayList<>();

        if(!validateHeading(heading)) {
            errors.add("The heading is either empty or too long! (Max length 1000 characters)");
        }

        if(!validateLead(lead)) {
            errors.add("The lead is either empty or too long! (Max length 5000 characters)");
        }

        if(!validateText(text)) {
            errors.add("The text is either empty or too long! (Max length 10 000 characters)");
        }

        if(!validateJournalists(journalists)) {
            errors.add("Add at least one journalist!");
        }

        if(!validateCategories(categories)) {
            errors.add("Add at least one category");
        }

        if(!validateImage(image)) {
            errors.add("Add an image!");
        }

        if(!validateImageSize(image)) {
            errors.add("Image file is too large!");
        }

        if(!validateImageType(image)) {
            errors.add("Image file type must be png!");
        }

        return errors;
    }

    private boolean validateImageType(MultipartFile file) {
        if(!file.getContentType().contains("png")) {
            return false;
        }
        return true;
    }

    private boolean validateImageSize(MultipartFile image) throws IOException {
        if(image.getBytes().length > 1048576) {
            return false;
        }
        return true;
    }

    private boolean validateText(String text) {
        if(text.isEmpty() ||text.length() > 10000) {
            return false;
        }
        return true;
    }

    private boolean validateCategories(String categories) {
        if(categories.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateJournalists(String journalists) {
        if(journalists.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateLead(String lead) {
        if(lead.isEmpty() ||lead.length()>5000) {
            return false;
        }
        return true;
    }

    private boolean validateHeading(String heading) {
        if(heading.isEmpty() ||heading.length() > 1000) {
            return false;
        }
        return true;
    }

    private boolean validateImage(MultipartFile image) {
        if(image.isEmpty()) {
            return false;
        }
        return true;
    }
}
