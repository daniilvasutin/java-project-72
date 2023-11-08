@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>
        <table class="table table-bordered table-hover mt-3">
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getUrl().getCreatedAtAsString()}</td>
            </tr>
        </table>

        <p>Проверки</p>
        <form method="post" action="/urls/${page.getUrl().getId()}/checks">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Код ответа</th>
                <th scope="col">title</th>
                <th scope="col">h1</th>
                <th scope="col">description</th>
                <th scope="col">Дата проверки</th>
            </tr>
            </thead>
            <tr>
                <th scope="row">...</th>
                <td>...</td>
                <td>...</td>
                <td>...</td>
                <td>...</td>
                <td>...</td>
            </tr>
        </table>
    </div>


<%--        --%><%--${NamedRoutes.productPath(product.getId())} в href--%>
<%--        <tbody>--%>
<%--        @for(var url : page.getUrls())--%>
<%--            <tr>--%>
<%--                <th scope="row">${url.getId()}</th>--%>
<%--                <td><a href="/">${url.getName()}</a></td>--%>
<%--                <td>${url.getCreatedAtAsString()}</td>--%>
<%--                <td>...</td>--%>
<%--            </tr>--%>
<%--        @endfor--%>
<%--        </tbody>--%>

`
)