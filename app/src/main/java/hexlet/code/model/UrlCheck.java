package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private Timestamp createdAt;

    public UrlCheck(Integer statusCode, String h1, String title, String description, Timestamp createdAt, Long urlId) {
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.urlId = urlId;
    }

    public String getCreatedAtAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String createdAtFormatted = sdf.format(createdAt);
        return createdAtFormatted;
    }
}
