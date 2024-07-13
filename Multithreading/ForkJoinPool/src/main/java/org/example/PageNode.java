package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PageNode {

    private String url;
    private ArrayList<PageNode> childNodes = new ArrayList<>();

    public PageNode(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void addChildUrl(String subUrl){
        childNodes.add(new PageNode(subUrl));
    }

    public ArrayList<PageNode> getChildNodes(){
        return childNodes;
    }

    private void buildSiteMap(PageNode node, int level, StringBuilder builder) {
        for (int i = 0; i < level; i++) {
            builder.append("\t");
        }
        builder.append(node.getUrl()).append("\n");

        for (PageNode child : node.getChildNodes()) {
            buildSiteMap(child, level + 1, builder);
        }
    }

    public void writeSiteMapToFile(String filePath) {
        StringBuilder builder = new StringBuilder();
        buildSiteMap(this, 0, builder);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}