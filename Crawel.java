import java.io.*;
import java.net.*;
import java.util.*;
public class Crawel {
    static LinkedList <URLDepthPair> findLink = new LinkedList<>(); //активные ссылки
    static LinkedList <URLDepthPair> viewedLink = new LinkedList<>(); // просмотренные ссылки

    public static void Process(String addressStart, int maxDepth) throws IOException {
        findLink.add(new URLDepthPair(addressStart, 0));
        while (!findLink.isEmpty()) {
            URLDepthPair current = findLink.removeFirst();
            if (current.depth <= maxDepth) {
                Socket socket = new Socket(current.getHost(), 443); // порт для https
                socket.setSoTimeout(5000);
                try {
                    Scan(current.getURL(), current.depth);
                    viewedLink.add(current);
                }
                catch (IOException e) {
                    viewedLink.add(current);
                }
                socket.close();
            }
        }
    }

    public static void Scan(String addressHad, int currentPrevious) throws IOException {
        URL address = new URL(addressHad);
        BufferedReader in = new BufferedReader(new InputStreamReader(address.openStream())); // Данный метод будет читать целую строку с другого конца соединения.
        String inputLine;
        while (((inputLine = in.readLine()) != null)) {
            if (inputLine.contains(URLDepthPair.URL_PREFIX)) {
                String linkToShow = inputLine.substring(inputLine.indexOf(URLDepthPair.URL_PREFIX) + 9);
                if ((linkToShow.startsWith("http://") || linkToShow.startsWith("https://")) && (!linkToShow.matches("^[а-яА-Я]+$"))) {
                    linkToShow = linkToShow.substring(0, linkToShow.indexOf('"'));
                    URLDepthPair catchPair = new URLDepthPair(linkToShow, currentPrevious + 1);
                    if ((URLDepthPair.check(findLink, catchPair)) && (URLDepthPair.check(viewedLink, catchPair))) {
                        findLink.add(catchPair);
                    }
                }
            }
        }
        in.close();
    }

    public static void showLinks(LinkedList<URLDepthPair> viewedLink) { // вывод
        for (URLDepthPair i: viewedLink)
            System.out.println("Глубина: " + i.getDepth() + "\tСсылка: " + i.getURL());
    }

    public static void main(String[] args) throws IOException {
        Scanner c = new Scanner(System.in);
        System.out.println("Глубина");
        int depth = c.nextInt();
        c = new Scanner(System.in);
        System.out.println("Ссылка");
        String st1 = c.nextLine();
        try {
            Process(st1, depth);
            showLinks(viewedLink);
        }
        catch (IOException e) {
            showLinks(viewedLink);
            System.out.println("Ошибка");
        }
    }
}