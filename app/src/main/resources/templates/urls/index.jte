@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`


<%--        <div class="mb-3">--%>
<%--            <a href="${NamedRoutes.buildProductPath()}">Добавить новый товар</a>--%>
<%--        </div>--%>
<div class="container-lg mt-5">
    <h1>Все Сайты</h1>
    <table class="table table-bordered table-hover mt-3">
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Имя</th>
                <th scope="col">Последняя проверка</th>
                <th scope="col">Код Ответа</th>
            </tr>
        </thead>
        <%--${NamedRoutes.productPath(product.getId())} в href--%>
        <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <th scope="row">${url.getId()}</th>
                    <td><a href="${NamedRoutes.selectUrlPath(url.getId())}">${url.getName()}</a></td>
                    <td>${url.getCreatedAtAsString()}</td>
                    <td>...</td>
                </tr>
            @endfor
        </tbody>
    </table>
</div>
    `
)