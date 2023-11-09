package hexlet.code.repository;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

    public static List<UrlCheck> getAllChecksById(Long id) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                Long PKid = resultSet.getLong("id");
                Long urlId = resultSet.getLong("url_id");
                Integer statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                UrlCheck newUrlCheck = new UrlCheck(statusCode, h1, title, description, createdAt, urlId);
                newUrlCheck.setId(PKid);
                result.add(newUrlCheck);
            }
            return result;
        }
    }
}
