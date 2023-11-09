package hexlet.code.repository;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck url) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var smtm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            smtm.setLong(1, url.getUrlId());
            smtm.setInt(2, url.getStatusCode());
            smtm.setString(3, url.getH1());
            smtm.setString(4, url.getTitle());
            smtm.setString(5, url.getDescription());
            smtm.setTimestamp(6, url.getCreatedAt());
            smtm.executeUpdate();

            var genKeys = smtm.getGeneratedKeys();
            if (genKeys.next()) {
                url.setId(genKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)){
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createAt = resultSet.getTimestamp("created_at");
                Url newUrl = new Url(name, createAt);
                newUrl.setId(id);
                result.add(newUrl);
            }
            return result;
        }
    }
}
