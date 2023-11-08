package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlsRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
                var smtm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            smtm.setString(1, url.getName());
            smtm.setTimestamp(2, url.getCreatedAt());
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

    public static Optional<Url> find(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createAt = resultSet.getTimestamp("created_at");
                Url url = new Url(name, createAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}