package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public final class Url {
    private Long id;
    private String name;
    private java.sql.Timestamp createdAt;
    public Url(String name, java.sql.Timestamp createdAt){
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getCreatedAtAsString() {
        return createdAt.toString();
    }
}
