package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlCheckController {
    public static void checkUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();

            Integer statusCode = response.getStatus();
            var doc = Jsoup.parse(response.getBody());
            var title = doc.title();
            var h1temp = doc.selectFirst("h1");
            var h1 = h1temp == null ? "" : h1temp.text();
            var descriptionTemp = doc.selectFirst("meta[name=description]");
            var description = descriptionTemp == null ? "" : descriptionTemp.attr("content");
            var createAt = new Timestamp(System.currentTimeMillis());

            UrlCheck urlCheck = new UrlCheck(statusCode, h1, title, description, createAt, url.getId());
            UrlCheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Ошибка в проверке сайта");
            ctx.sessionAttribute("flash-type", "danger");
            e.printStackTrace();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", e.getMessage());
            ctx.sessionAttribute("flash-type", "danger");
        }

        ctx.redirect(NamedRoutes.selectedUrlPath(url.getId()));
    }
}
