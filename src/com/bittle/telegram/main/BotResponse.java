package com.bittle.telegram.main;

import com.bittle.telegram.text.*;
import com.bittle.telegram.WebCrawler;
import org.javia.arity.MathSolver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscartorres on 6/27/17.
 */

// only called by BotMaintainer when it doesn't find any response,
// this class has all commands that are not admin or game related
class BotResponse {
    private StringCommands stringCommands = new StringCommands();

    String getTextResponse(Update update) {
        String str = update.getMessage().getText();
        String lower = StringUtils.removeCall(str.toLowerCase());
        String result = stringCommands.get(lower);

        if(!result.isEmpty()){
            return result;
        }
        if (lower.startsWith("/say ")) {
            result = str.substring(str.indexOf(" ") + 1);

        } else if (lower.startsWith("/caps ")) {
            result = str.substring(str.indexOf(" ") + 1);
            result = result.toUpperCase();

        } else if (lower.startsWith("/lower ")) {
            result = str.substring(str.indexOf(" ") + 1);
            result = result.toLowerCase();
        } else if (lower.startsWith("/math ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = MathSolver.solve(restOfString);

        } else if (lower.startsWith("/ly ")) {
            int index = lower.indexOf("_");
            if (index < 0) {
                result = "No _ found!\n\nExample:\n/ly Eminem_Beautiful";
            } else {
                String restOfString = lower.substring(lower.indexOf(" ") + 1).trim();
                String artist = restOfString.substring(0, restOfString.indexOf("_"));
                String songName = restOfString.substring(restOfString.indexOf("_") + 1);

                result = getSongLyrics(artist, songName);
            }
        } else if (lower.startsWith("/dict ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = getMerianDefinition(restOfString);

        }
        //else if(Moderator.containsBadWord(lower)) {
        //    result = "Bad words found: " + Moderator.getBadwordsFromString(lower);
        //}
        else if ((str.matches("(.)\\1+") || (str.length() == 1 && str.charAt(0) == '^'))
                && !str.isEmpty() && !containsEmoji(str)) {
            // check if string is only made up of one char, if it is
            // replay with a longer string of the same char
            result = str + "" + str.charAt(0);
        } else if ((lower.startsWith("no ") && lower.endsWith(" u")) || lower.equals("u")) {
            // message: no u
            // reply: no no u
            String scrapedText = lower.replaceAll("\\s+", " ");
            scrapedText = scrapedText.replaceAll("no ", "");
            if (scrapedText.equals("u")) {
                result = lower.replace("u", " no u").
                        replaceAll("\\s+", " ");
            }
        } else if (lower.startsWith("/ud ")) {
            // get the urban dictionary text using Json
            String words = str.substring(str.indexOf(" ") + 1);
            result = getUrbanDictionaryDef(words).trim();

        } else if (lower.startsWith("/wiki ")) {
            // get the wiki text using Json
            String words = str.substring(str.indexOf(" ") + 1);
            result = getWikiSearch(words).trim();

        } else if (!str.startsWith("/") && str.endsWith("ing")) {
            result = str.substring(0, str.lastIndexOf("ing")) + "ong";
        } else if (!str.startsWith("/") && str.endsWith("ong")) {
            result = str.substring(0, str.lastIndexOf("ong")) + "ing";

        } else if (lower.startsWith("/8ball ")) {
            result = "8ball: ";
            int rand = (int) (Math.random() * 19);
            //System.out.println(rand);

            if (rand == 0)
                result += "It is certain";

            if (rand == 1)
                result += "It is decidedly so";

            if (rand == 2)
                result += "Without a doubt";

            if (rand == 3)
                result += "Yes, definitely";

            if (rand == 4)
                result += "You may rely on it";

            if (rand == 5)
                result += "As I see it, yes";

            if (rand == 6)
                result += "Most likely";

            if (rand == 7)
                result += "Outlook good";

            if (rand == 8)
                result += "Yes";

            if (rand == 9)
                result += "Signs point to yes";

            if (rand == 10)
                result += "Reply hazy, try again";

            if (rand == 11)
                result += "Ask again later";

            if (rand == 12)
                result += "Better not tell you now";

            if (rand == 13)
                result += "Cannot predict now";

            if (rand == 14)
                result += "Concentrate and ask again";

            if (rand == 15)
                result += "Don't count on it";

            if (rand == 16)
                result += "My reply is no";

            if (rand == 17)
                result += "My sources say no";

            if (rand == 18)
                result += "Outlook not so good";

            else if (rand > 18)
                result += "Very doubtful";

        } else if (lower.startsWith("/u ")) {
            String restOfString = str.substring(str.indexOf("/u") + 2);
            result = StringUtils.StringToUnicode(restOfString);
        } else if (lower.startsWith("/c ")) {

            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u200c\ud83c\udde6").replaceAll("b", "\u200c\ud83c\udde7").replaceAll("c", "\u200c\ud83c\udde8").replaceAll("d", "\u200c\ud83c\udde9").replaceAll("e", "\u200c\ud83c\uddea").replaceAll("f", "\u200c\ud83c\uddeb").replaceAll("g", "\u200c\ud83c\uddec").replaceAll("h", "\u200c\ud83c\udded").replaceAll("i", "\u200c\ud83c\uddee").replaceAll("j", "\u200c\ud83c\uddef").replaceAll("k", "\u200c\ud83c\uddf0").replaceAll("l", "\u200c\ud83c\uddf1").replaceAll("m", "\u200c\ud83c\uddf2").replaceAll("n", "\u200c\ud83c\uddf3").replaceAll("o", "\u200c\ud83c\uddf4").replaceAll("p", "\u200c\ud83c\uddf5").replaceAll("q", "\u200c\ud83c\uddf6").replaceAll("r", "\u200c\ud83c\uddf7").replaceAll("s", "\u200c\ud83c\uddf8").replaceAll("t", "\u200c\ud83c\uddf9").replaceAll("u", "\u200c\ud83c\uddfa").replaceAll("v", "\u200c\ud83c\uddfb").replaceAll("w", "\u200c\ud83c\uddfc").replaceAll("x", "\u200c\ud83c\uddfd").replaceAll("y", "\u200c\ud83c\uddfe").replaceAll("z", "\u200c\ud83c\uddff").replace("?", "\u200c\ud83c\u2754").replaceAll("!", "\u200c\ud83c\u2755");
        } else if (lower.startsWith("/f ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u03b1").replaceAll("b", "\u0432").replaceAll("c", "\u00a2").replaceAll("d", "\u2202").replaceAll("e", "\u0454").replaceAll("f", "\u0192").replaceAll("g", "g").replaceAll("h", "\u043d").replaceAll("i", "\u03b9").replaceAll("j", "j").replaceAll("k", "\u043a").replaceAll("l", "\u2113").replaceAll("m", "m").replaceAll("n", "\u03b7").replaceAll("o", "\u03c3").replaceAll("p", "\u03c1").replaceAll("q", "q").replaceAll("r", "\u044f").replaceAll("s", "\u0455").replaceAll("t", "\u0442").replaceAll("u", "\u03c5").replaceAll("v", "\u03bd").replaceAll("w", "\u03c9").replaceAll("x", "\u03c7").replaceAll("y", "\u0443").replaceAll("z", "z");
        } else if (lower.startsWith("/g ")) {
            String temp = "https://google.com/search?q=+";
            String restOfString = str.substring(str.indexOf(" ") + 1);
            restOfString = restOfString.replaceAll("\\s+", "+");
            result = temp + restOfString;
        } else if (lower.startsWith("/j ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u4e39").replaceAll("b", "\u4e43").replaceAll("c", "\u531a").replaceAll("d", "\u5200").replaceAll("e", "\u30e2").replaceAll("f", "\u4e0b").replaceAll("g", "\u30e0").replaceAll("h", "\u5344").replaceAll("i", "\u5de5").replaceAll("j", "\uff2a").replaceAll("k", "\uff2b").replaceAll("l", "\u3125").replaceAll("m", "\u722a").replaceAll("n", "\u308c").replaceAll("o", "\u53e3").replaceAll("p", "\u3117").replaceAll("q", "\uff31").replaceAll("r", "\u5c3a").replaceAll("s", "\uff33").replaceAll("t", "\u5315").replaceAll("u", "\u222a").replaceAll("v", "\u2228").replaceAll("w", "\u5c71").replaceAll("x", "\u30e1").replaceAll("y", "\u311a").replaceAll("z", "\u4e59").replaceAll(" ", "   ");
        } else if (lower.startsWith("/o ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u24d0").replaceAll("b", "\u24d1").replaceAll("c", "\u24d2").replaceAll("d", "\u24d3").replaceAll("e", "\u24d4").replaceAll("f", "\u24d5").replaceAll("g", "\u24d6").replaceAll("h", "\u24d7").replaceAll("i", "\u24d8").replaceAll("j", "\u24d9").replaceAll("k", "\u24da").replaceAll("l", "\u24db").replaceAll("m", "\u24dc").replaceAll("n", "\u24dd").replaceAll("o", "\u24de").replaceAll("p", "\u24df").replaceAll("q", "\u24e0").replaceAll("r", "\u24e1").replaceAll("s", "\u24e2").replaceAll("t", "\u24e3").replaceAll("u", "\u24e4").replaceAll("v", "\u24e5").replaceAll("w", "\u24e6").replaceAll("x", "\u24e7").replaceAll("y", "\u24e8").replaceAll("z", "\u24e9").replaceAll("0", "\u3267").replaceAll("1", "\u278a").replaceAll("2", "\u278b").replaceAll("3", "\u278c").replaceAll("4", "\u278d").replaceAll("5", "\u278e").replaceAll("6", "\u278f").replaceAll("7", "\u2790").replaceAll("8", "\u2791").replaceAll("9", "\u2792");
        }

        return result;
    }

    private boolean containsEmoji(String str) {
        String regex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";

        Matcher matchEmo = Pattern.compile(regex).matcher(str);

        return matchEmo.find();
    }

    // uses WebCrawler.java to parse the Json text from the urban dictionary API
    // Then gets the definition and returns it as text
    private String getUrbanDictionaryDef(String words) {
        String url = "http://api.urbandictionary.com/v0/define?term=" +
                words.trim().replaceAll("\\s+", "+");
        WebCrawler reader = new WebCrawler(url);
        String def;
        try {
            JSONArray jsonArray = reader.getJsonFromHtml().getJSONArray("list");
            int random = new Random().nextInt(jsonArray.length());
            def = jsonArray.getJSONObject(random).get("definition").toString();

            return def;
        } catch (org.json.JSONException e) {
            return "No definition found.";
        }
    }

    // uses WebCrawler.java to parse the Json text from the wikipedia API
    // Then gets the definition and returns it as text
    private String getWikiSearch(String words) {
        String words2 = words.trim().replaceAll("\\s+", "%20");
        String url = "https://en.wikipedia.org/w/api.php?format=json&action=" +
                "query&prop=extracts&exintro=&explaintext=&titles=" + words2;


        WebCrawler reader = new WebCrawler(url);
        String def;
        try {
            //System.out.println(reader.getJsonObject().toString());
            //JSONArray jsonArray = reader.getJsonObject().getJSONArray("RelatedTopics");
            JSONObject obj = reader.getJsonFromHtml().getJSONObject("query");
            obj = obj.getJSONObject("pages");

            String wiki_ID = obj.toString();
            wiki_ID = wiki_ID.substring(wiki_ID.indexOf("\"") + 1);
            wiki_ID = wiki_ID.substring(0, wiki_ID.indexOf("\""));
            System.out.println(wiki_ID);

            obj = obj.getJSONObject(wiki_ID);
            def = obj.getString("extract");

            return "/wiki " + words + "\n\n" + def;
        } catch (org.json.JSONException e) {
            return "No definition found.";
        }

    }

    private String getMerianDefinition(String word) {
        String str = Dictionary.searchOnlineWord(word);

        if (str.toLowerCase().trim().equals("error"))
            return "No definition found for " + word + ".";
        else
            return str.trim();
    }

    private String getSongLyrics(String artist, String songName) {
        AZLyrics az = new AZLyrics(artist, songName);
        try {
            return az.getSongName() + " by " + az.getArtist() + ":\n\n" + az.getLyrics();
        } catch (Exception e) {
            return "No lyrics found for " + songName + " by " + artist + ".";
        }
    }
}
