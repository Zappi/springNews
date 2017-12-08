package wad.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class News extends AbstractPersistable<Long> {

    @Size(max = 1000)
    private String heading;
    @Size(max = 5000)
    private String lead;   //ingressi
    @OneToOne
    @Lob
    private Image image;
    @Size(max = 10000)
    private String text;
    private int pageOpened = 0;
    private LocalDateTime localTime;
    @ManyToMany
    private List<Journalist> journalistList;
    @ManyToMany
    private List<Category> categoryList;

    public News(String heading, String lead, String text, LocalDateTime localTime) {
        this.heading = heading;
        this.lead = lead;
        this.text = text;
        this.localTime = localTime;
        this.categoryList = new ArrayList<>();
        this.journalistList = new ArrayList<>();
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPageOpened(int pageOpened) {
        this.pageOpened = pageOpened;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getReleaseTime() {
        return localTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.localTime = releaseTime;
    }

    public List<Journalist> getJournalistList() {
        return journalistList;
    }

    public void setJournalistList(List<Journalist> journalistList) {
        this.journalistList = journalistList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public int getPageOpened() {
        return pageOpened;
    }

    public void incrementPageOpened() {
        pageOpened+=1;
    }
}
