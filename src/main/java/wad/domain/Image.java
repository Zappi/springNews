package wad.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Image extends AbstractPersistable<Long> {

    @Lob
    @Size(max = Integer.MAX_VALUE)
    private byte[] content;
    @OneToOne
    private News news;
    private LocalDateTime localTime;

    public Image(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }


    public LocalDateTime getReleaseTime() {
        return localTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.localTime = releaseTime;
    }
}