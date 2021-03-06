package wad.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Category extends AbstractPersistable<Long> {

    private String name;
    @ManyToMany(mappedBy = "categoryList")
    private List<News> newsList;


    public Category(String name) {
        this.name = name;
        this.newsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transactional
    public List<News> getNewsList() {
        return newsList;
    }



}
