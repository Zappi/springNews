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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        if (!super.equals(o)) return false;

        News news = (News) o;

        if (getPageOpened() != news.getPageOpened()) return false;
        if (getHeading() != null ? !getHeading().equals(news.getHeading()) : news.getHeading() != null) return false;
        if (getLead() != null ? !getLead().equals(news.getLead()) : news.getLead() != null) return false;
        if (getImage() != null ? !getImage().equals(news.getImage()) : news.getImage() != null) return false;
        if (getText() != null ? !getText().equals(news.getText()) : news.getText() != null) return false;
        if (localTime != null ? !localTime.equals(news.localTime) : news.localTime != null) return false;
        if (getJournalistList() != null ? !getJournalistList().equals(news.getJournalistList()) : news.getJournalistList() != null)
            return false;
        return getCategoryList() != null ? getCategoryList().equals(news.getCategoryList()) : news.getCategoryList() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getHeading() != null ? getHeading().hashCode() : 0);
        result = 31 * result + (getLead() != null ? getLead().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + getPageOpened();
        result = 31 * result + (localTime != null ? localTime.hashCode() : 0);
        result = 31 * result + (getJournalistList() != null ? getJournalistList().hashCode() : 0);
        result = 31 * result + (getCategoryList() != null ? getCategoryList().hashCode() : 0);
        return result;
    }
}
