package wad.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class News extends AbstractPersistable<Long> {

    private String heading;
    private String lead;   //ingressi
    //@Lob
    //private byte[] image;
    private String text;
    private LocalDate releaseTime;

    //MISSING A JOURNALIST AND CATEGORIES

}
