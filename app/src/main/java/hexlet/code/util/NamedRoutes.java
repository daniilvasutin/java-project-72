package hexlet.code.util;

public class NamedRoutes {
    public static String indexPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String selectedUrlPath(Long id) {
        return selectedUrlPath(String.valueOf(id));
    }

    public static String selectedUrlPath(String id) {
        return "/urls/" + id;
    }

    public static String checkUrlPath(Long id) {
        return checkUrlPath(String.valueOf(id));
    }

    public static String checkUrlPath(String id) {
        return "/urls/" + id + "/checks";
    }
}
