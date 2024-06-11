package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

public class ImagesDownloader {
    static Set<String> links = new HashSet<String>();
    public static void getImages() {
        String url = "https://skillbox.ru/";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img");
            for (Element image : images){
                links.add(image.attr("abs:src"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        links.forEach(System.out::println);
    }

    public static void downloadImages() {
        try {
            for (String link : links){
            URLConnection connection = new URL(link).openConnection();
            InputStream inStream = connection.getInputStream();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
