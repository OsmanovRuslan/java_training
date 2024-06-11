package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesFinder {

    private List<String> paths = new ArrayList<>();
    public List<String> findFiles(String path) {
        File file = new File(path);
        deepIntoFolder(file);
        return paths;
    }

    private void deepIntoFolder(File file){
        if(file.isFile()){
            checkFileExtensions(file);
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deepIntoFolder(f);
                }
            }
        }
    }

    private void checkFileExtensions(File file){
        String fileName = file.getName();
        String[] fileNameMassive = fileName.split("\\.");
        if (fileNameMassive[fileNameMassive.length - 1].equals("json") || fileNameMassive[fileNameMassive.length - 1].equals("csv")){
            paths.add(file.getAbsolutePath());
        }
    }
}
