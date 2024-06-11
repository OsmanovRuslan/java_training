package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadHTML {

    public static void readHtml() {

        String htmlFile = parseFile("data/code.html");
        Document doc = Jsoup.parse(htmlFile);
        Elements elements = doc.select("a.start-screen-directions__link");
        elements.forEach(element -> {
            System.out.println(element.text());
        });
//        System.out.println(htmlFile);
    }

    public static String parseFile(String path) {
        StringBuilder builder = new StringBuilder();
        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
