package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public final class Url {
    private Long id;
    private String name;
    private java.sql.Timestamp createdAt;
    private List<UrlCheck> urlChecks = new ArrayList<>();

    public Url(String name, java.sql.Timestamp createdAt){
        this.name = name;
        this.createdAt = createdAt;
    }

    public Url(String name, java.sql.Timestamp createdAt, List<UrlCheck> urlChecks){
        this.name = name;
        this.createdAt = createdAt;
        this.urlChecks = urlChecks;
    }

    public void addUrlCheck(UrlCheck urlCheck) {
        urlChecks.add(urlCheck);
    }

    public String getCreatedAtAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String createdAtFormatted = sdf.format(createdAt);
        return createdAtFormatted;
    }
}
