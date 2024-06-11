package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

public class Main {
    private static int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "C:/Java/Skillbox/Thread/src";
        String dstFolder = "C:/Java/Skillbox/Thread/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int middle = files.length / 2;

        File[] files1 = new File[middle];
        System.arraycopy(files, 0, files1, 0, middle);

        ImageResizer imageResizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
//        For EXTENDS Thread
//        imageResizer1.start();
//        For IMPLEMENTS Runnable
        new Thread(imageResizer1).start();

        File[] files2 = new File[files.length - middle];
        System.arraycopy(files, middle, files2, 0, files2.length);
        ImageResizer imageResizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
//        For EXTENDing Thread
//        imageResizer2.start();
//        For IMPLEMENTS Runnable
        new Thread(imageResizer2).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++){
                System.out.println(i);
            }
        }).start();
    }
}