package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataclasses.*;

import java.io.File;
import java.util.*;

public class DataAdder {
    // Классы
    private FilesFinder filesFinder = new FilesFinder();
    private JsonParser jsonParser = new JsonParser();
    private CsvParser csvParser = new CsvParser();
    private ParsingWeb parsingWeb = new ParsingWeb();

    // Списки данных
    private List<String> listOfFilesPaths;
    private List<Depths> listOfDepths = new ArrayList<>();
    private List<DateLines> listOfDateLines = new ArrayList<>();
    private List<Line> listOfLine = new ArrayList<>();
    private List<Station> listOfStation = new ArrayList<>();

    // для станций по линиям
    private Map<String, List<String>> stationsByLine = new HashMap<>();

    // для второго json с информацией о станциях
    private List<Object> stationsList = new ArrayList<>();

    // для Jackson
    private ObjectMapper objectMapper = new ObjectMapper();

    public DataAdder() {
        getData();
        prepareData();
    }

    private void getData(){
        listOfFilesPaths = filesFinder.findFiles("C:/Users/marle/Desktop/stations-data");
        for (String path : listOfFilesPaths) {
            if (path.split("\\.")[1].equals("csv")){
                listOfDateLines.addAll(csvParser.readCsv(path));
            } else{
                listOfDepths.addAll(jsonParser.readJson(path));
            }
        }

        listOfLine = parsingWeb.parseLine();
        listOfStation = parsingWeb.parseStation();
    }

    private void prepareData() {
        // заполняю все списки
        for (Station station : listOfStation) {
            if (!stationsByLine.containsKey(station.getNumberOfLine())) {
                stationsByLine.put(station.getNumberOfLine(), new ArrayList<>(List.of(station.getName())));
            } else {
                stationsByLine.get(station.getNumberOfLine()).add(station.getName());
            }
        }
        // Preparing для второго json
        String name = "";
        String line = "";
        String depth = "?";
        String date = "";
        for (Station s : listOfStation) {
            name = s.getName();
            String numberOfLine = s.getNumberOfLine();
            for (Line l : listOfLine){
                if(numberOfLine.equals(l.getNumber())){
                    line = l.getName();
                }
            }
            for (Depths d : listOfDepths){
                if (d.getStationName().equals(name)){
                    depth = d.getDepth().replace(",", ".");
                }
            }
            for(DateLines dl : listOfDateLines){
                if (dl.getName().equals(name)){
                    date = dl.getDate();
                }
            }
            if (depth.equals("?")){
                stationsList.add(new StationsWithoutDepth(name, line, date));
            }else {
                stationsList.add(new Stations(name, line, date, Double.parseDouble(depth)));
            }

        }

    }

    public void generateFirstJson(String path){
        MetroData metroData = new MetroData(stationsByLine, listOfLine);
        try{
            objectMapper.writeValue(new File(path), metroData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateSecondJson(String path){
        try{
            objectMapper.writeValue(new File(path), stationsList);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
