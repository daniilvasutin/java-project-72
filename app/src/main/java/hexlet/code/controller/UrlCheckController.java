package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

public class UrlCheckController {
    public static void makeCheckUrl(Context ctx) throws SQLException, IOException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        //ДЕЛАЕМ ЧЕК САЙТА
        var statusCode = Jsoup.connect(url.getName()).execute().statusCode();
        var doc = Jsoup.connect(url.getName()).get();
        var title = doc.title();
        var h1temp = doc.selectFirst("h1");
        var h1 = h1temp == null ? "" : h1temp.text();
        var descriptionTemp = doc.selectFirst("meta[name=description]");
        var description = descriptionTemp == null ? "" : descriptionTemp.attr("content");
        Date utilDate = new Date();
        Timestamp createAt = new Timestamp(utilDate.getTime());
        //КОНЕЦ ЧЕКА САЙТА

        UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, createAt, url.getId());
        UrlCheckRepository.save(urlCheck);

        var checkedUrls = UrlCheckRepository.getAllChecksById(url.getId());
        url.setUrlCheck(checkedUrls);

        UrlPage page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }
}
