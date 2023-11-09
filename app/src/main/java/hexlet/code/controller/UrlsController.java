package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class UrlsController {
    public static void check(Context ctx) throws SQLException, MalformedURLException {
        var form = ctx.formParamAsClass("url", String.class).get();

        URL urlFromForm = new URL(form);
        String protocol = urlFromForm.getProtocol();
        String symbol = "://";
        String host = urlFromForm.getHost();
        String portSymbol = urlFromForm.getPort() == -1 ? "" : ":";
        String port = urlFromForm.getPort() == -1 ? "" : String.valueOf(urlFromForm.getPort());

        var name = protocol + symbol + host + portSymbol + port;

        Date utilDate = new Date();
        Timestamp createAt = new Timestamp(utilDate.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

        var checkedUrls = UrlCheckRepository.getAllChecksById(url.getId());

        var page = new UrlPage(url, checkedUrls);
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }
}
