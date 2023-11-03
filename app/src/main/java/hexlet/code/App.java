package hexlet.code;

import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static Javalin getApp() throws IOException, SQLException {

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
