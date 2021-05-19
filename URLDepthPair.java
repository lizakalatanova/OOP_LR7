import java.util.*;
import java.net.*;
public class URLDepthPair { // класс для представления пар [URL, depth]
    public static final String URL_PREFIX = "<a href=";
    public String URL;
    public int depth;

    public URLDepthPair (String URL, int depth){  // конструктор
        this.URL=URL;
        this.depth=depth;
    }

    public String getHost() throws MalformedURLException { // получаем хост
        URL host = new URL(URL);
        return host.getHost();
    }

    public int getDepth() { // получаем глубину
        return depth;
    }

    public String getURL() { // получаем ссылку
        return URL;
    }

    public static boolean check(LinkedList<URLDepthPair> findRef, URLDepthPair pair) { // проверка на просмотр
        boolean isAlready = true;
        for (URLDepthPair i: findRef)
            if (i.getURL().equals(pair.getURL())) {
                isAlready = false;
                break;
            }
        return isAlready;
    }
}