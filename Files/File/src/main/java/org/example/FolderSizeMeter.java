package org.example;

import java.io.File;
import java.util.Scanner;

public class FolderSizeMeter {
    static int sum = 0;
    public static void task() {

        while (true) {
            System.out.println("Введите путь к папке: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            getFolderSize(file);
            getHumanReadableSize(sum);
            sum = 0;
        }
    }

    public static void getFolderSize(File file){
        if(file.isFile()){
            sum += file.length();
            System.out.println("File size: " + file.length());
        } else{
            File[] files = file.listFiles();
            for (File f : files){
                getFolderSize(f);
            }
        }

    }

    public static void getHumanReadableSize(int summary) {
        double Kb = summary / 1024;
        if (summary / 1024 >= 1024){
            if (Kb >= 1024) {
                double Mb = Kb / 1024;
                if (Mb >= 1024) {
                    double Gb = Mb / 1024;
                    if (Gb >= 1024) {
                        System.out.println("Too much :)");
                    }
                    else System.out.printf("%.2f Gb \n", Gb);
                }
                else System.out.printf("%.2f Mb \n", Mb);
            }
            else System.out.printf("%.2f Kb \n", Kb);
        }
        else System.out.printf("%.2f Bytes \n", summary);
    }
}
