package wad.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Image extends AbstractPersistable<Long> {

    @OneToOne
    @JoinColumn
    private News news;

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String contentType;

    private Long contentLength;
    private LocalDateTime localTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;


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

    public News getNews() {
        return news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }
}