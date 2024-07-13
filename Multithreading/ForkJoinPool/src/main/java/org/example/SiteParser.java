package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class SiteParser extends RecursiveTask<PageNode> {
    private PageNode node;
    private final String domain;
    private static Set<String> uniqueUrls = new HashSet<String>();

    public SiteParser(PageNode node, String domain) {
        this.node = node;
        this.domain = domain;
        uniqueUrls.add(node.getUrl());
    }

    @Override
    protected PageNode compute() {
        try {
            Thread.sleep(100);
            Document doc = Jsoup.connect(node.getUrl()).userAgent("Chrome").get();
            Elements links = doc.select("a");

            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((subUrl) -> {
                if (subUrl.contains(domain) && subUrl.endsWith("/")) {
                    boolean added;
                    synchronized (uniqueUrls) {
                        added = uniqueUrls.add(subUrl);
                    }

                    if (added) {
                        node.addChildUrl(subUrl);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<SiteParser> tasks = new ArrayList<SiteParser>();
        node.getChildNodes().forEach(childNode -> {
            SiteParser task = new SiteParser(childNode, domain);
            task.fork();
            tasks.add(task);
        });

        for (SiteParser task : tasks) {
            task.join();
        }

        return node;
    }
}
