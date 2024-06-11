package org.example;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Write {

    //Write file using FileOutputStream // Устарелая фигня, запись только через байты или коды символов
    public static void writeFile1(){
        try{
            FileOutputStream os = new FileOutputStream("data/info2.txt");
            os.write(1); //Запись только через байты или коды символов
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Write file using PrintWriter
    public static void writeFile2(){
        try {
            PrintWriter printWriter = new PrintWriter("data/info2.txt");
            for (int i =0; i < 10; i++) {
                printWriter.write(i + "\n");
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Write file using Files
    public static void writeFile3(){
        try {
            ArrayList<String> strings = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                strings.add(Integer.toString(i));
            }
            Files.write(Paths.get("data/info3.txt"), strings);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
