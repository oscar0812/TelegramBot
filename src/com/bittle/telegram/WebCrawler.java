package com.bittle.telegram;
/*
 * Created by oscartorres on 6/22/17.
 */
import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;
import org.json.JSONObject;

// goes to the internet and returns json or html
public class WebCrawler {
    private String html = "";

    public WebCrawler(String url) {
        try {
            html = readUrl(url);

        } catch (Exception e) {
            System.out.println("ERROR READING URL\n" + e.toString());
        }
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                builder.append(chars, 0, read);

            return builder.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }

    public String getHtml(){
        return html;
    }

    public JSONObject getJsonFromHtml(){
        return new JSONObject(html);
    }

}