package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataclasses.Depths;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonParser {
    private List<Depths> listOfDepths = new ArrayList<>();
    public List<Depths> readJson(String path){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = Files.readString(Paths.get(path));
            listOfDepths = objectMapper.readValue(json, new TypeReference<>(){});
            return listOfDepths;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return listOfDepths;
    }
}
