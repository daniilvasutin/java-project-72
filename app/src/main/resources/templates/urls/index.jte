@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`

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
        <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <th scope="row">${url.getId()}</th>
                    <td><a href="${NamedRoutes.selectedUrlPath(url.getId())}">${url.getName()}</a></td>
                    @if(page.getLastChecks() != null && page.getLastChecks().get(url.getId()) != null)
                        <td>${page.getLastChecks().get(url.getId()).getStatusCode()}</td>
                        <td>${page.getLastChecks().get(url.getId()).getCreatedAtAsString()}</td>
                    @else
                        <td scope="col"></td>
                        <td scope="col"></td>
                    @endif
                </tr>
            @endfor
        </tbody>
    </table>
</div>
    `
)