package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;
import repository.BaseRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class App {

    private static final String JDBC_URL_DEFAULT = "jdbc:h2:mem:hexlet_project;";


    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static String getJdbcUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", JDBC_URL_DEFAULT);
    }

    public static Javalin getApp() throws IOException, SQLException {

        HikariConfig configHikari = new HikariConfig();
        configHikari.setJdbcUrl(getJdbcUrl());
        var dataSource = new HikariDataSource(configHikari);
        // Получаем путь до файла в src/main/resources
        var url = App.class.getClassLoader().getResource("schema.sql");
        //создаем запрос рендеря файл schema.sql
        var file = new File(url.getFile());
        var sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));
        // Получаем соединение, создаем стейтмент и выполняем запрос
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()){
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

//        # У строки в переменной следующий формат: {driver}:{provider}://{host}:{port}/{db}?password={password}&user={user}
//        export JDBC_DATABASE_URL=jdbc:postgresql://db:5432/postgres?password=password&user=postgres
//        postgres://pageanalyzer_6zju_user:1a0KvVU9M2JEB2Jtxd3VvJmSsYrZMctN@dpg-cl3oko1novjs73bkvekg-a/pageanalyzer_6zju
//        postgres://pageanalyzer_6zju_user:1a0KvVU9M2JEB2Jtxd3VvJmSsYrZMctN@dpg-cl3oko1novjs73bkvekg-a.oregon-postgres.render.com/pageanalyzer_6zju


        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get("/", App::HelloWord);
        return app;
    }

    private static void HelloWord(Context ctx) {
        ctx.result("Hello World");
    }
}
