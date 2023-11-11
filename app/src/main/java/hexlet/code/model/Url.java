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
    private Timestamp createdAt;

    public Url(String name){
        this.name = name;
    }

    public String getCreatedAtAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String createdAtFormatted = sdf.format(createdAt);
        return createdAtFormatted;
    }
}
