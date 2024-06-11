package org.example;

import java.io.File;

public class Main {
    private static int newWidth = 300;

    public static void main(String[] args) {
        // получаем кол-во ядер
        int cores = Runtime.getRuntime().availableProcessors();

        String srcFolder = "C:/Java/Skillbox/Multithreading/src";
        String dstFolder = "C:/Java/Skillbox/Multithreading/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        // Если файлов меньше чем ядер, тогда используем меньшее кол-во ядер.
        int counter = Math.min(cores, files.length);
        // кол-во файлов на одно ядро
        int parts = files.length / counter;
        // позиция с которой начинается копирование файлов
        int srcPos = 0;

        for (int i = 1; i <= counter; i++) {
            File[] temp;
            // Если запускается последнее ядро, то в массив файлов добавляем все оставшиеся файлы
            if (i == counter){
                temp = new File[files.length - (parts * (counter - 1))];
            } else {
                temp = new File[parts];
            }
            System.arraycopy(files, srcPos, temp, 0, temp.length);
            srcPos = srcPos + temp.length;
            ImageResizer resizer = new ImageResizer(temp, newWidth, dstFolder, start);
            new Thread(resizer).start();
        }

    }
}