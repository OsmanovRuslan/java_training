package org.example;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String domain = ""; // сайт
        PageNode root = new PageNode(domain);
        PageNode result = new ForkJoinPool().invoke(new SiteParser(root, domain));
        result.writeSiteMapToFile("data/site_map.txt");
    }
}