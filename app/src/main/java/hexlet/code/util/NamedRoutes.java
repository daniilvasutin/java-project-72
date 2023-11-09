package hexlet.code.util;

public class NamedRoutes {
    public static String indexPath() {
        return "/";
    }
    public static String checkUrlPath() {
        return "/urls";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String selectUrlPath(Long id) {
        return selectUrlPath(String.valueOf(id));
    }

    public static String selectUrlPath(String id) {
        return "/urls/" + id;
    }

    public static String makeCheckUrlPath(Long id) { return makeCheckUrlPath(String.valueOf(id));}

    public static String makeCheckUrlPath(String id) { return "/urls/" + id + "/checks";}
}
