package org.example;

import org.example.dataclasses.Line;
import org.example.dataclasses.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParsingWeb {

    private List<Line> listOfLines = new ArrayList<>();
    private List<Station> listOfStations = new ArrayList<>();

    private Document doc;
    public ParsingWeb(){
        String html = getHtmlCode();
        doc = Jsoup.parse(html);
    }

    public List<Station> parseStation(){
        // Поиск станций метро, создание объекта Station и добавление его в listOfStations
        for (Line line : listOfLines) {
            String lineNumber = line.getNumber();
            Elements station = doc.select("div.js-metro-stations.t-metrostation-list-table[data-line=" + lineNumber + "]");
            Elements stations = station.select("span.name");
            int sizeOfStationInLine = stations.size();
            for (int i = 0; i < sizeOfStationInLine; i++){
                listOfStations.add(new Station(stations.get(i).text(), lineNumber));
            }
        }
        return listOfStations;
    }

    public List<Line> parseLine() {
        // Поиск линий метро, создание объекта Line и добавление его в listOfLines
        Elements lines = doc.select("span.js-metro-line.t-metrostation-list-header.t-icon-metroln");
        lines.forEach(line -> {
            String numberOfLine = line.attr("data-line");
            listOfLines.add(new Line(line.text(), numberOfLine));

        });
        return listOfLines;
    }


    // Метод получения кода HTML страницы используя Jsoup
    private String getHtmlCode() {
        String path = "https://skillbox-java.github.io/";
        StringBuilder builder = new StringBuilder();
        try{
            Document doc = Jsoup.connect(path).get();
            builder.append(doc.html());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
