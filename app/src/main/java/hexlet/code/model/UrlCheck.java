package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class UrlCheck {
    private Long id;
    private Long urlId;
    private Integer statusCode;
    private String h1;
    private String title;
    private String description;
    private java.sql.Timestamp createdAt;

    public UrlCheck(Integer statusCode, String h1, String title, String description, Timestamp createdAt, Long urlId) {
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.urlId = urlId;
    }
}
