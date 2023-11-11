package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.IndexController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlsController;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import hexlet.code.repository.BaseRepository;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;

import gg.jte.resolve.ResourceCodeResolver;

import java.io.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

import hexlet.code.util.NamedRoutes;

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

    public static boolean isProduction() {
        return System.getenv().getOrDefault("APP_ENV", "dev").equals("production");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    public static Javalin getApp() throws IOException, SQLException {

//        System.out.println("GET APP START!!!");
        HikariConfig configHikari = new HikariConfig();
        configHikari.setJdbcUrl(getJdbcUrl());

        if (isProduction()) {
            var username = System.getenv("JDBC_DATABASE_USERNAME");
            var password = System.getenv("JDBC_DATABASE_PASSWORD");
            configHikari.setUsername(username);
            configHikari.setPassword(password);
        }

        var dataSource = new HikariDataSource(configHikari);
        // Получаем путь до файла в src/main/resources
        //создаем запрос рендеря файл schema.sql
        var inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        var sql = reader.lines().collect(Collectors.joining("\n"));
//        System.out.println("!!! SQL !!! \n" + sql + "!!! END SQL !!! \n");

        // Получаем соединение, создаем стейтмент и выполняем запрос
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()){
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        JavalinJte.init(createTemplateEngine());

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get(NamedRoutes.indexPath(), IndexController::index);
        app.post(NamedRoutes.checkUrlPath(), UrlsController::addSite);
        app.get(NamedRoutes.urlsPath(), UrlsController::showAllUrls);
        app.get(NamedRoutes.selectUrlPath("{id}"), UrlsController::showSelectedUrl);
        app.post(NamedRoutes.makeCheckUrlPath("{id}"), UrlCheckController::makeCheckUrl);
//        app.post(NamedRoutes.ckeckSelectedUrlPath(), UrlController::ckeckSelectedUrl);

        return app;
    }


}
