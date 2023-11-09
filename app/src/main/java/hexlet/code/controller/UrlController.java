package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class UrlController {
    public static void check(Context ctx) throws SQLException, MalformedURLException {
        var form = ctx.formParamAsClass("url", String.class).get();

        URL urlFromForm = new URL(form);
        String protocol = urlFromForm.getProtocol();
        String symbol = "://";
        String host = urlFromForm.getHost();
        String portSymbol = urlFromForm.getPort() == -1 ? "" : ":";
        String port = urlFromForm.getPort() == -1 ? "" : String.valueOf(urlFromForm.getPort());

        var name = protocol + symbol + host + portSymbol + port;

//        var name = url.getProtocol() + "://" + url.getHost() + ;


//        var name = ctx.formParamAsClass("url", URL.class).get();
//                .check(
//                        value -> !value.isEmpty(), "Url не должен быть пустым").get();



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
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void makeCheckUrl(Context ctx) throws SQLException, IOException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        var statusCode = Jsoup.connect(url.getName()).execute().statusCode();
        var doc = Jsoup.connect(url.getName()).get();
        var title = doc.title();
        var h1temp = doc.selectFirst("h1");
        var h1 = h1temp == null ? "" : h1temp.text();
        var descriptionTemp = doc.selectFirst("meta[name=description]");
        var description = descriptionTemp == null ? "" : descriptionTemp.attr("content");
        Date utilDate = new Date();
        Timestamp createAt = new Timestamp(utilDate.getTime());

        UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, createAt, url.getId());
        url.addUrlCheck(urlCheck);
        UrlCheckRepository.save(urlCheck);


        UrlPage page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

//    public static void ckeckSelectedUrl(Context ctx) throws SQLException {
//        var id = ctx.pathParamAsClass("id", Long.class).get();
//        var url = UrlsRepository.find(id).orElseThrow(() -> new NotFoundResponse("Url not found"));
//
//
//        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
//    }
}
