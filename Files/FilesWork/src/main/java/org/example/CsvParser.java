package org.example;

import org.example.dataclasses.DateLines;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    private List<DateLines> listOfDateLines = new ArrayList<>();
    public List<DateLines> readCsv(String path){
        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines){
                String[] fragments = line.split(",");
                if (fragments.length != 2){
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                listOfDateLines.add(new DateLines(fragments[0], fragments[1]));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return listOfDateLines;
    }
}
