package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

public class UrlsController {
    public static void addSite(Context ctx) throws SQLException {
        var urlFormAsString = ctx.formParamAsClass("url", String.class).get()
                .toLowerCase().trim();

        String normalizedUrl;
        try {
            URL urlAsUrl = new URL(urlFormAsString);
            normalizedUrl = normalizeUrl(urlAsUrl);
        } catch (IllegalArgumentException | IOException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "warning");
            ctx.redirect(NamedRoutes.indexPath());
            return;
        }

        if (UrlsRepository.doesUrlExist(normalizedUrl)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            Date utilDate = new Date();
            Timestamp createAt = new Timestamp(utilDate.getTime());

            var url = new Url(normalizedUrl, createAt);
            UrlsRepository.save(url);
            ctx.sessionAttribute("flash", "Страница добавлена успешно");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    private static String normalizeUrl(URL url) throws MalformedURLException {


        String protocol = url.getProtocol();
        String symbol = "://";
        String host = url.getHost();
        String portSymbol = url.getPort() == -1 ? "" : ":";
        String port = url.getPort() == -1 ? "" : String.valueOf(url.getPort());

        var name = protocol + symbol + host + portSymbol + port;
        return name;
    }

    public static void showAllUrls(Context ctx) throws SQLException {
        var urls = UrlsRepository.getEntities();
        var lastChecks = UrlCheckRepository.getListOfLastChecks();
        UrlsPage page = new UrlsPage(urls, lastChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void showSelectedUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        var checkedUrls = UrlCheckRepository.getAllChecksById(url.getId());

        var page = new UrlPage(url, checkedUrls);
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }
}
