package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Read {

    // Read file using FileInputStream
    public static void readFile1(){
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream is = new FileInputStream("data/info.txt");
            for (;;) {
                int c = is.read();
                if (c < 0) {
                    break;
                }
                char ch = (char) c;
                sb.append(ch);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sb);
    }


    // Read file using BufferedReader
    public static void readFile2(){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/info.txt"));
            for (;;) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + "\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sb);
    }

    // read file using Files
    public static void readFile3(){
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get("data/info.txt"));
            lines.forEach(line -> sb.append(line + "\n"));
//            for (String line : lines){
//                sb.append(line);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sb);
    }
}
