package com.bittle.internet.pastebin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Pastebin {
    public static String getRaw(String url){

        if(!url.contains("pastebin.com")){
            url = "https://pastebin.com/"+url;
        }

        String html = "";
        try {
            html =  Jsoup.connect(url).get().html();
        }catch (Exception e){
            e.printStackTrace();
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
