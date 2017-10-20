package com.bittle.telegram.text;

import com.bittle.telegram.WebCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Pastebin {
    public static String getRaw(String url){
        String html;
        try {
            html = new WebCrawler(url).getHtml();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

        Document document = Jsoup.parse(html);
        // get all list elements
        Element code_section = document.select("#code_frame").first().select("ol").first();
        Elements children = code_section.children();
        StringBuilder builder = new StringBuilder();
        for(int x = 0; x<children.size(); x++){
            builder.append(children.eq(x).text());
            builder.append("\n");
        }
        builder.deleteCharAt(builder.length()-1); /* delete last \n */
        return builder.toString();
    }
}
