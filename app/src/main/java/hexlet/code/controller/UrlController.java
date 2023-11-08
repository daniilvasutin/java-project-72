package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class UrlController {
    public static void check(Context ctx) throws SQLException {
        var name = ctx.formParamAsClass("url", String.class).get();
//                .check(
//                        value -> !value.isEmpty(), "Url не должен быть пустым").get();

        java.util.Date utilDate = new java.util.Date();
        java.sql.Timestamp createAt = new java.sql.Timestamp(utilDate.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.println(sdf.format(createAt));

        var url = new Url(name, createAt);
        UrlsRepository.save(url);
        ctx.sessionAttribute("flash", "Url был успешно добавлен!");
        ctx.sessionAttribute("flash-type", "success");
        var urls = UrlsRepository.getEntities();
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void showAllUrls(Context ctx) throws SQLException {
        var urls = UrlsRepository.getEntities();
        UrlsPage page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void showSelectUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }
}
