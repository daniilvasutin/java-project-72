@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
    page = page,
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
    <%--            @if(page.getUrl().getUrlChecks() == null)--%>
                @if(page.getUrlCheck() != null)
                    @for(var urlCheck : page.getUrlCheck())
                        <tr>
                            <th scope="row">${urlCheck.getId()}</th>
                            <td>${urlCheck.getStatusCode()}</td>
                            <td>${urlCheck.getTitle()}</td>
                            <td>${urlCheck.getH1()}</td>
                            <td>${urlCheck.getDescription()}</td>
                            <td>${urlCheck.getCreatedAtAsString()}</td>
                        </tr>
                    @endfor
                @endif


    <%--            @else--%>
    <%--                @for(var urlCheck : page.getUrlCheck())--%>
    <%--                    <tr>--%>
    <%--                        <th scope="col">${urlCheck.getId()}</th>--%>
    <%--                        <th scope="col">${urlCheck.getStatusCode()}</th>--%>
    <%--                        <th scope="col">${urlCheck.getTitle()}</th>--%>
    <%--                        <th scope="col">${urlCheck.getH1()}</th>--%>
    <%--                        <th scope="col">${urlCheck.getDescription()}</th>--%>
    <%--                        <th scope="col">${urlCheck.getCreatedAt().toString()}</th>--%>
    <%--                    </tr>--%>
    <%--                @endfor--%>
    <%--            @endif--%>
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