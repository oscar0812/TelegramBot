package com.bittle.telegram.Main;

import com.bittle.telegram.Text.StringUtils;
import com.bittle.telegram.Text.AZLyrics;
import com.bittle.telegram.Text.Dictionary;
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
public class BotResponse {
    private Update mainUpdate;

    public String getTextResponse(Update update) {
        mainUpdate = update;
        String str = update.getMessage().getText();
        return getTextResponse(str, "");
    }

    private String getTextResponse(String str, String result) {
        String lower = removeCall(str.toLowerCase());

        String user = mainUpdate.getMessage().getFrom().getUserName();

        String chat_id_with_lex = "-1001125322153";
        boolean is_lex_chat = ((mainUpdate.getMessage().getChat().getId()+"").equals(chat_id_with_lex)
                                || mainUpdate.getMessage().getFrom().getUserName().equals("Lexilovestacos"));

        if (lower.equals("/commands")) {

            result = "Commands:\n\n/bittle" + (is_lex_chat ? "\n/lexi" : "") + "\n/toco\n/info\n/status\n/options\n/text" +
                    "\n/games\n/joke\n/roast <name>\n/mock <text>\n" +
                    "/ly <artist> <song name>\n/py <python script>\n/math <equation>\n" +
                    "/ud <text> [urban dictionary]\n/dict <word> [Meriam-Webster]\n/wiki <text> [Wikipedia]";

        } else if (lower.equals("/bittle")) {
            result = "Bittle is my creator! You can chat and ask questions at\n\nt.me/OGBittle";

        } else if (lower.equals("/lexi") && (user.equals("OGBittle") || user.equals("Lexilovestacos"))) {
            result = "She\'s Bittle\'s babe :D\n\nOnly reason why this bot exists tbh\n\nt.me/Lexilovestacos";

        } else if (lower.equals("/toco")) {
            result = "\"You knew damn well I was a snake... BEFORE YOU TOOK ME IN!!!\"\n\nt.me/lntegrity";

        } else if(lower.equals("/status")){
            result = MainClass.perGroupBotConfig.getSettings();

        } else if(lower.equals("/options")){
            result = "The following are bot options:\n\nTo start or stop games:\n/games on\n" +
                    "/games off\n\nTo set max text length:\n/maxtext <number>" +
                    "\n\nTo allow/disallow other bots in chat:\n/antibot on\n/antibot off" +
                    "\n\nTo allow/disallow members to join without profile pictures:\n/pfp on\n/pfp off"+
                    "\n\nTo allow/disallow bot to greet new members:\n/welcome off\n/welcome on" +
                    "\n\nTo allow/disallow bot to say farewell to members:\n/bye off\n/bye on" +
                    "\n\nTo set greeting of new members " +
                    "(type _user_ where you want the username to go):\n/setwelcome <text>" +
                    "\n\nTo set farewell message " +
                    "(type _user_ where you want the username to go):\n/setbye <text>";

        }else if(lower.equals("/welcome")) {
            String onOff = MainClass.perGroupBotConfig.isWelcomeOn() ? "on" : "off";
            result = "Welcome message is " + onOff + ".\n\nWelcome message: \n" +
                    "" + MainClass.perGroupBotConfig.getWelcomeMessage() + "\n\nTo set welcome message:\n/setwelcome <text>";

        } else if(lower.equals("/bye")) {
            String onOff = MainClass.perGroupBotConfig.isByeOn() ? "on" : "off";
            result = "Bye message is " + onOff + ".\n\nBye message: \n" +
                    "" + MainClass.perGroupBotConfig.getByeMessage() + "\n\nTo set welcome message:\n/setwelcome <text>";

        }else if (lower.startsWith("/say ")) {
            // just repeats what user says after !say
            result = str.substring(str.indexOf(" ") + 1);

        } else if (lower.startsWith("/caps ")) {
            // capitalize a string
            result = str.substring(str.indexOf(" ") + 1);
            result = result.toUpperCase();

        } else if (lower.startsWith("/lower ")) {
            // lowercase a string
            result = str.substring(str.indexOf(" ") + 1);
            result = result.toLowerCase();
        } else if (lower.equals("/text")) {
            result = "Text commands:\n\n/say <text>\n/lower <text>\n/caps <text>\n/b <text>\n/bx <binary>\n" +
                    "/c <text>\n/d <text>\n/f <text>\n/g <text>\n/h <text>\n/j <text>\n/o <text>\n/r <text>\n/u <text>";
        } else if (lower.startsWith("/math ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = MathSolver.solve(restOfString);

        } else if (lower.equals("/math")) {
            result = "Solve math equations!\n\nExample:\n!math 3+4+8*(pi*sin(60))+3";

        } else if (lower.equals("/games")) {
            result = "Games:\n\n/type\n/scramble\n/taboo\n/brick\n/hangman\n\nTo see game rules " +
                    "type info after the game style, like so:\n\n/type info";

        }else if(lower.equals("/ly")){
            result = "Get song lyrics!\n\nExample:\n\n/ly Eminem_Beautiful\n\nMust contain _ " +
                    "to separate artist from song name";
        }
        else if(lower.startsWith("/ly ")) {
            int index = lower.indexOf("_");
            if (index < 0) {
                result = "No _ found!\n\nExample:\n/ly Eminem_Beautiful";
            } else {
                String restOfString = lower.substring(lower.indexOf(" ")+1).trim();
                String artist = restOfString.substring(0, restOfString.indexOf("_"));
                String songName = restOfString.substring(restOfString.indexOf("_")+1);

                result = getSongLyrics(artist, songName);
            }
        }
        else if(lower.equals("/dict")){
            result = "Get a definition from a professional online dictionary!\n\nExample:\n\n/dict hello";
        }
        else if(lower.startsWith("/dict ")){
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = getMerianDefinition(restOfString);

        }
        else if (lower.equals("/scores")) {
            result = "To get game scores send:\n\n/scores type\n/scores scramble\n" +
                    "/scores taboo\n/scores brick\n/scores hangman\n\n" +
                    "Or do\n\n/scores all\n\nTo get all scores in private message.";

        } else if (lower.trim().replaceAll("\\s+", " ").equals("/type info")) {

            result = "Type game info: \n\nTo play send /type in any group, " +
                    "first person to type the given word wins! To get scores do\n\n/scores type";

        } else if (lower.trim().replaceAll("\\s+", " ").equals("/scramble info")) {

            result = "Scramble game info: \n\nTo play send /scramble in any group, " +
                    "first person to unscramble the given scrambled word wins! To get scores do\n\n/scores scramble";

        } else if (lower.trim().replaceAll("\\s+", " ").equals("/taboo info")) {

            result = "Taboo game info: \n\nTo play send /taboo in any group, " +
                    "a word will be sent to the person who sent /taboo. That person will " +
                    "gives clues to the group about that word, but if the person accidentally mentions " +
                    "the word the game is over. To get scores do\n\n/scores taboo";

        } else if (lower.trim().replaceAll("\\s+", " ").equals("/brick info")) {
            result = "Brick game info: \n\nTo play send /brick in any group, " +
                    "a brick will be added to the wall! Keep building to keep " +
                    "unwanted threats out!";

        }else if (lower.trim().replaceAll("\\s+", " ").equals("/hangman info")) {
            result = "Hangman game info: \n\nTo play send /hangman in any group, " +
                    "guess the word, or guess character by character until the game finishes." +
                    " Be aware that you have only 7 tries! To guess a character simply send a message" +
                    " with only one character.";

        }

        else if (lower.equals("kitty")) {
            result = "Meow!";
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
        }
        else if(lower.equals("/ud")){
            result = "Get a definition from urban dictionary!\n\nExample:\n\n/ud bittle";
        }
        else if (lower.startsWith("/ud ")) {
            // get the urban dictionary text using Json
            String words = str.substring(str.indexOf(" ") + 1);
            result = getUrbanDictionaryDef(words).trim();

        } else if (lower.startsWith("/wiki ")) {
            // get the wiki text using Json
            String words = str.substring(str.indexOf(" ") + 1);
            result = getWikiSearch(words).trim();

        } else if (!str.startsWith("!") && str.endsWith("ing")) {
            result = str.substring(0, str.lastIndexOf("ing")) + "ong";
        } else if (!str.startsWith("!") && str.endsWith("ong")) {
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

        } else if(lower.startsWith("/u ")){
            String restOfString = str.substring(str.indexOf("/u")+2);
            result = StringUtils.StringToUnicode(restOfString);
        }

        else if (lower.startsWith("/b ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "01100001\ufeff").replaceAll("b", "01100010\ufeff").replaceAll("c", "01100011\ufeff").replaceAll("d", "01100100\ufeff").replaceAll("e", "01100101\ufeff").replaceAll("f", "01100110\ufeff").replaceAll("g", "01100111\ufeff").replaceAll("h", "01101000\ufeff").replaceAll("i", "01101001\ufeff").replaceAll("j", "01101010\ufeff").replaceAll("k", "01101011\ufeff").replaceAll("l", "01101100\ufeff").replaceAll("m", "01101101\ufeff").replaceAll("n", "01101110\ufeff").replaceAll("o", "01101111\ufeff").replaceAll("p", "01110000\ufeff").replaceAll("q", "01110001\ufeff").replaceAll("r", "01110010\ufeff").replaceAll("s", "01110011\ufeff").replaceAll("t", "01110100\ufeff").replaceAll("u", "01110101\ufeff").replaceAll("v", "01110110\ufeff").replaceAll("w", "01110111\ufeff").replaceAll("x", "01111000\ufeff").replaceAll("y", "01111001\ufeff").replaceAll("z", "01111010\ufeff").replaceAll(" ", "00100000\ufeff");
        } else if (lower.startsWith("/bx ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("01100001\ufeff", "a").replaceAll("01100010\ufeff", "b").replaceAll("01100011\ufeff", "c").replaceAll("01100100\ufeff", "d").replaceAll("01100101\ufeff", "e").replaceAll("01100110\ufeff", "f").replaceAll("01100111\ufeff", "g").replaceAll("01101000\ufeff", "h").replaceAll("01101001\ufeff", "i").replaceAll("01101010\ufeff", "j").replaceAll("01101011\ufeff", "k").replaceAll("01101100\ufeff", "l").replaceAll("01101101\ufeff", "m").replaceAll("01101110\ufeff", "n").replaceAll("01101111\ufeff", "o").replaceAll("01110000\ufeff", "p").replaceAll("01110001\ufeff", "q").replaceAll("01110010\ufeff", "r").replaceAll("01110011\ufeff", "s").replaceAll("01110100\ufeff", "t").replaceAll("01110101\ufeff", "u").replaceAll("01110110\ufeff", "v").replaceAll("01110111\ufeff", "w").replaceAll("01111000\ufeff", "x").replaceAll("01111001\ufeff", "y").replaceAll("01111010\ufeff", "z").replaceAll("00100000\ufeff", " ");

        } else if (lower.startsWith("/c ")) {

            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u200c\ud83c\udde6").replaceAll("b", "\u200c\ud83c\udde7").replaceAll("c", "\u200c\ud83c\udde8").replaceAll("d", "\u200c\ud83c\udde9").replaceAll("e", "\u200c\ud83c\uddea").replaceAll("f", "\u200c\ud83c\uddeb").replaceAll("g", "\u200c\ud83c\uddec").replaceAll("h", "\u200c\ud83c\udded").replaceAll("i", "\u200c\ud83c\uddee").replaceAll("j", "\u200c\ud83c\uddef").replaceAll("k", "\u200c\ud83c\uddf0").replaceAll("l", "\u200c\ud83c\uddf1").replaceAll("m", "\u200c\ud83c\uddf2").replaceAll("n", "\u200c\ud83c\uddf3").replaceAll("o", "\u200c\ud83c\uddf4").replaceAll("p", "\u200c\ud83c\uddf5").replaceAll("q", "\u200c\ud83c\uddf6").replaceAll("r", "\u200c\ud83c\uddf7").replaceAll("s", "\u200c\ud83c\uddf8").replaceAll("t", "\u200c\ud83c\uddf9").replaceAll("u", "\u200c\ud83c\uddfa").replaceAll("v", "\u200c\ud83c\uddfb").replaceAll("w", "\u200c\ud83c\uddfc").replaceAll("x", "\u200c\ud83c\uddfd").replaceAll("y", "\u200c\ud83c\uddfe").replaceAll("z", "\u200c\ud83c\uddff").replace("?", "\u200c\ud83c\u2754").replaceAll("!", "\u200c\ud83c\u2755");
        } else if (lower.startsWith("/d ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\ufeff\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\\\u00a0\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0_\u00a0\u00a0\\\u00a0\u00a0\n\u00a0\u00a0/\u00a0\u00a0___\u00a0\u00a0\\\u00a0\n/_/\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\\_\\\n").replaceAll("b", "\ufeff\u00a0______\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0__\u00a0\u00a0\u00a0\u00a0\u00a0)\u00a0\n|\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0\u00a0|_)\u00a0\u00a0\u00a0\u00a0\u00a0|\n|______/\n").replaceAll("c", "\ufeff\u00a0\u00a0\u00a0\u00a0_____\u00a0\n\u00a0/\u00a0\u00a0\u00a0____|\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0|____\u00a0\n\u00a0\\______|\n").replaceAll("d", "\ufeff\u00a0\u00a0_____\u00a0\n|\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|_\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|______/\n").replaceAll("e", "\ufeff\u00a0______\u00a0\n|\u00a0\u00a0\u00a0\u00a0____|\n|\u00a0\u00a0\u00a0\u00a0___|\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0|____\u00a0\n|______|\n").replaceAll("f", "\ufeff\u00a0_____\u00a0\n|\u00a0\u00a0\u00a0\u00a0___|\n|\u00a0\u00a0\u00a0\u00a0|__\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0\u00a0__|\u00a0\u00a0\n|__|\n").replaceAll("g", "\ufeff\u00a0\u00a0\u00a0____\u00a0\n\u00a0/\u00a0\u00a0___|\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0_\u00a0\n|\u00a0\u00a0\u00a0|_|\u00a0\u00a0\u00a0|\n\u00a0\\____|\n").replaceAll("h", "\ufeff\u00a0\ufeff_\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|_|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0|\n|_\u00a0|\u00a0\u00a0|\u00a0_|\n").replaceAll("i", "\ufeff\u00a0______\u00a0\n|__\u00a0\u00a0\u00a0\u00a0__|\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0|\u00a0\n\u00a0__|\u00a0\u00a0|__\n|______|\n").replaceAll("j", "\ufeff\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|__|\u00a0\u00a0\u00a0|\n\u00a0\\_____/\n").replaceAll("k", "\ufeff\u00a0__\u00a0\u00a0\u00a0\u00a0__\n|\u00a0\u00a0\u00a0\u00a0|/\u00a0\u00a0\u00a0/\n|\u00a0\u00a0\u00a0\u00a0\'\u00a0\u00a0\u00a0/\u00a0\n|\u00a0\u00a0\u00a0\u00a0.\u00a0\u00a0\u00a0\\\u00a0\n|__|\\__\\\n").replaceAll("l", "\ufeff\u00a0__\u00a0\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0|___\u00a0\n|______|\n").replaceAll("m", "\ufeff\u00a0__\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0__\u00a0\n|\u00a0\u00a0\u00a0\u00a0\u00a0\\./\u00a0\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|\\./|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0_\u00a0|\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0_\u00a0|\n").replaceAll("n", "\ufeff\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\n|\u00a0\u00a0\u00a0\\\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0\u00a0\\|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|\\\u00a0\u00a0\u00a0\u00a0\u00a0|\n|__|\u00a0\u00a0\\__|\n").replaceAll("o", "\ufeff\u00a0\u00a0\u00a0\u00a0___\u00a0\u00a0\n\u00a0/\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|\u00a0_\u00a0|\u00a0\u00a0\u00a0|\n\u00a0\\\u00a0___\u00a0\u00a0/\n").replaceAll("p", "\ufeff\u00a0____\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0|_)\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0__\u00a0/\u00a0\n|__|\n").replaceAll("q", "\ufeff\u00a0\u00a0\u00a0\u00a0___\n\u00a0/\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|_\u00a0|\u00a0\u00a0\u00a0|\n\u00a0\\___\\_\\\n").replaceAll("r", "\ufeff\u00a0____\u00a0\u00a0\n|\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0|_)\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0\u00a0,\u00a0\u00a0\u00a0<\u00a0\n|\u00a0_\u00a0|\u00a0\\\u00a0_\u00a0\\\n").replaceAll("s", "\ufeff\u00a0\u00a0\u00a0____\u00a0\u00a0\n/\u00a0\u00a0\u00a0___|\u00a0\n\\___\u00a0\u00a0\u00a0\\\u00a0\n\u00a0___\u00a0)\u00a0\u00a0\u00a0)\n|_____/\n").replaceAll("t", "\ufeff\u00a0\ufeff_______\u00a0\n|__\u00a0\u00a0\u00a0\u00a0\u00a0__|\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0_|\n").replaceAll("u", "\ufeff\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0|\u00a0_\u00a0|\u00a0\u00a0\u00a0|\n\u00a0\\_____/\n").replaceAll("v", "\ufeff__\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0__\n\\\u00a0\u00a0\u00a0\\\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0/\n\u00a0\u00a0\\\u00a0\u00a0\u00a0\\/\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0\u00a0\\\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\\\u00a0/\n").replaceAll("w", "\ufeff\u00a0__\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0__\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0_\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\n|\u00a0\u00a0\u00a0\u00a0|/\u00a0\u00a0\\|\u00a0\u00a0\u00a0\u00a0|\n\u00a0|__\u00a0/\'\\__\u00a0|\n").replaceAll("x", "\ufeff__\u00a0\u00a0\u00a0\u00a0\u00a0__\n\\\u00a0\u00a0\u00a0\\/\u00a0\u00a0\u00a0/\n\u00a0\u00a0\\\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0/\u00a0\u00a0\u00a0,\u00a0\u00a0\u00a0\\\u00a0\n/_\u00a0/\u00a0\u00a0\\\u00a0_\\\n").replaceAll("y", "\ufeff__\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0__\n\\\u00a0\u00a0\u00a0\\\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0/\n\u00a0\u00a0\\\u00a0\u00a0\u00a0V\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0_\u00a0|\n").replaceAll("z", "\ufeff\u00a0______\n|___\u00a0\u00a0\u00a0\u00a0\u00a0/\n\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0/__\u00a0\n/______|\n").replaceAll("0", "\ufeff\u00a0\u00a0\u00a0\u00a0___\u00a0\u00a0\n\u00a0/\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\n|\u00a0\u00a0\u00a0|\u00a0_\u00a0|\u00a0\u00a0\u00a0|\u00a0\n\u00a0\\\u00a0___\u00a0\u00a0/\n").replaceAll("1", "\ufeff\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\n\u00a0/\u00a0\u00a0\u00a0\u00a0|\n\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0|\n\u00a0_|\u00a0\u00a0\u00a0|_\n|\u00a0____|\n").replaceAll("2", "\ufeff\u00a0____\u00a0\u00a0\u00a0\n|___\u00a0\u00a0\u00a0\\\u00a0\n\u00a0\u00a0__\u00a0)\u00a0\u00a0\u00a0|\n/\u00a0\u00a0\u00a0\u00a0__/\n|_____|\n").replaceAll("3", "\ufeff\u00a0_____\u00a0\u00a0\n|___\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0\u00a0|_\u00a0\u00a0\u00a0\\\u00a0\u00a0\n\u00a0___\u00a0)\u00a0\u00a0|\n|_____/\n").replaceAll("4", "\ufeff\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0|\u00a0\u00a0|\u00a0\u00a0\u00a0|\u00a0\u00a0\n|\u00a0\u00a0\u00a0|_|\u00a0\u00a0\u00a0|_\u00a0\n|___\u00a0\u00a0\u00a0\u00a0\u00a0_|\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\u00a0_|\n").replaceAll("5", "\ufeff\u00a0_____\u00a0\u00a0\u00a0\n|\u00a0\u00a0\u00a0___|\u00a0\u00a0\n|___\u00a0\u00a0\u00a0\\\u00a0\u00a0\n\u00a0___\u00a0)\u00a0\u00a0\u00a0|\n|_____/\n").replaceAll("6", "\ufeff\u00a0\u00a0\u00a0__\u00a0\u00a0\u00a0\u00a0\n\u00a0/\u00a0\u00a0\u00a0/__\u00a0\n|\u00a0\u00a0\u00a0\'\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0\u00a0\u00a0O\u00a0\u00a0\u00a0\u00a0|\n\u00a0\\_____/\n").replaceAll("7", "\ufeff\u00a0______\u00a0\u00a0\n|___\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\n\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\n\u00a0/_\u00a0\u00a0/\n").replaceAll("8", "\ufeff\u00a0______\u00a0\u00a0\n|___\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0|\n\u00a0\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0\u00a0/\u00a0\n\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\n\u00a0/_\u00a0\u00a0/\n").replaceAll("9", "\ufeff\u00a0\u00a0\u00a0\u00a0___\u00a0\n\u00a0/\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\\\u00a0\n|\u00a0\u00a0\u00a0\u00a0O\u00a0\u00a0\u00a0\u00a0|\n\u00a0\\__,\u00a0\u00a0\u00a0\u00a0|\n\u00a0\u00a0\u00a0\u00a0\u00a0/_\u00a0/\n").replace("?", "\ufeff\u00a0___\u00a0\n|__\u00a0\u00a0\u00a0\\\n\u00a0\u00a0\u00a0\u00a0/\u00a0\u00a0\u00a0/\n\u00a0\u00a0\u00a0|\u00a0_\u00a0|\u00a0\n\u00a0\u00a0\u00a0(\u00a0_)\n").replaceAll("$", "\ufeff\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\n\u00a0\u00a0\u00a0|\u00a0\u00a0\u00a0\u00a0|\u00a0\n/\u00a0\u00a0\u00a0__\u00a0)\n\\__\u00a0\u00a0\u00a0\u00a0\\\n(_\u00a0\u00a0\u00a0\u00a0\u00a0_/\n\u00a0\u00a0\u00a0|\u00a0_\u00a0|\n").replaceAll("&", "\ufeff____\u00a0\u00a0\n\u00a0(\u00a0\u00a0\u00a0\u00a0o\u00a0\u00a0\u00a0)\u00a0\u00a0\n\u00a0/\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\\/\\\n|\u00a0\u00a0\u00a0\u00a0\u00a0O\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0<\n\u00a0\\_____/\\/\n").replaceAll("#", "\ufeff\u00a0\u00a0\u00a0__\u00a0\u00a0\u00a0\u00a0\u00a0__\u00a0\u00a0\u00a0\n\u00a0_|\u00a0\u00a0\u00a0|__|\u00a0\u00a0\u00a0|_\u00a0\n|_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0_|\n|_\u00a0\u00a0\u00a0\u00a0\u00a0__\u00a0\u00a0\u00a0\u00a0\u00a0_|\n\u00a0\u00a0\u00a0|\u00a0_|\u00a0\u00a0\u00a0\u00a0|_\u00a0|\n").replaceAll(" ", "\n\n\n\n");
        } else if (lower.startsWith("/f ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u03b1").replaceAll("b", "\u0432").replaceAll("c", "\u00a2").replaceAll("d", "\u2202").replaceAll("e", "\u0454").replaceAll("f", "\u0192").replaceAll("g", "g").replaceAll("h", "\u043d").replaceAll("i", "\u03b9").replaceAll("j", "j").replaceAll("k", "\u043a").replaceAll("l", "\u2113").replaceAll("m", "m").replaceAll("n", "\u03b7").replaceAll("o", "\u03c3").replaceAll("p", "\u03c1").replaceAll("q", "q").replaceAll("r", "\u044f").replaceAll("s", "\u0455").replaceAll("t", "\u0442").replaceAll("u", "\u03c5").replaceAll("v", "\u03bd").replaceAll("w", "\u03c9").replaceAll("x", "\u03c7").replaceAll("y", "\u0443").replaceAll("z", "z");
        } else if (lower.startsWith("/g ")) {
            String temp = "https://google.com/search?q=+";
            String restOfString = str.substring(str.indexOf(" ") + 1);
            restOfString = restOfString.replaceAll("\\s+", "+");
            result = temp + restOfString;
        } else if (lower.startsWith("/h ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u2584\u2584\u2584\n\u2592\u2588\u2588\u2588\u2588\u2584\n\u2592\u2588\u2588\u00a0\u00a0\u2580\u00a0\u00a0\u00a0\u2588\u2584\n\u2591\u2588\u2588\u2584\u2584\u2584\u2584\u2588\u2588\n\u00a0\u2593\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\u2592\n\u00a0\u2592\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2592\u2588\n\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u2592\n\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n").replaceAll("b", "\ufeff\u00a0\u2584\u2584\u2584\u2584\n\u2593\u2588\u2588\u2588\u2588\u2588\u2584\n\u2592\u2588\u2588\u2592\u00a0\u2584\u2588\u2588\n\u2592\u2588\u2588\u2591\u2588\n\u00a0\u2593\u2588\u00a0\u00a0\u00a0\u00a0\u2580\u2588\u2593\n\u00a0\u2592\u2593\u2588\u2588\u2588\u2592\n\u00a0\u2592\u2591\u2592\n\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u2591\n").replaceAll("c", "\ufeff\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u2584\u2588\u2588\u2588\u2588\u2584\n\u2592\u2588\u2588\u2580\u00a0\u2580\u00a0\u00a0\u00a0\u00a0\u2588\n\u2592\u2593\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2584\n\u2592\u2593\u2593\u2584\u00a0\u2584\u2588\u2588\u2592\u00a0\n\u2592\u00a0\u2593\u2588\u2588\u2588\n\u2591\u00a0\u2591\u2592\u00a0\u2592\n\u00a0\u00a0\u2591\u00a0\u00a0\u2592\n\u2591\n\u2591\u00a0\u2591\n\u2591\n").replaceAll("d", "\u2593\u2588\u2588\u2588\u2588\u2588\u00a0\n\u2592\u2588\u2588\u2580\u00a0\u00a0\u00a0\u00a0\u2588\u258c\n\u2591\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u258c\n\u2591\u2593\u2588\u2584\u00a0\u00a0\u00a0\u00a0\u2588\u258c\n\u2591\u2592\u2588\u2588\u2588\u2588\u2593\n\u00a0\u2592\u2592\u2593\u00a0\u00a0\u2592\u00a0\u2592\n\u00a0\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u2591\u00a0\u2591\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u2591\n\u00a0\u2591\n").replaceAll("e", "\u2593\u2588\u2588\u2588\u2588\u2588\n\u2593\u2588\n\u2592\u2588\u2588\u2588\n\u2592\u2593\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u2584\u00a0\u00a0\u00a0\u00a0\n\u2591\u2592\u2588\u2588\u2588\u2588\u2592\n\u2591\u2591\u00a0\u2592\n\u00a0\u2591\u00a0\u2591\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n").replaceAll("f", "\u2588\u2588\u2588\u2588\u2588\u2592\n\u2593\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2592\u2588\u2588\u2588\u2588\n\u2591\u2593\u2588\u2592\n\u2591\u2592\u2588\n\u00a0\u2592\n\u00a0\u2591\n\n").replaceAll("g", "\u2584\u2588\u2588\u2588\u2588\n\u00a0\u2588\u2588\u2592\u00a0\u00a0\u00a0\u2588\u2592\n\u2592\u2588\u2588\u2591\u00a0\u00a0\u2584\u2584\u2584\n\u2593\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2593\n\u00a0\u2592\u2593\u2588\u2588\u2588\u2580\u2592\n\u00a0\u2591\u2592\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u2591\n\u2591\u00a0\u2591\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("h", "\ufeff\u00a0\u2588\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\n\u2593\u2588\u2593\u00a0\u2591\u00a0\u00a0\u2588\u2588\u2593\n\u2592\u2588\u2588\u2588\u2588\u2588\u2593\n\u2591\u2593\u2588\u2593\u00a0\u00a0\u2588\u2588\u2592\n\u2591\u2593\u2588\u2592\u2591\u00a0\u2588\u2588\u2592\n\u00a0\u2592\u00a0\u2591\u2591\u2592\u2591\u00a0\u2592\n\u00a0\u2592\n\u00a0\u2592\u00a0\u00a0\u2592\u2591\u00a0\u2591\n\u00a0\u2592\n\u00a0\u2591\u00a0\u00a0\u2591\u2591\u00a0\u2591\n\u00a0\u2591\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n").replaceAll("i", "\ufeff\u00a0\u2588\u2588\u2593\n\u2593\u2588\u2588\u2592\n\u2592\u2588\u2588\u2592\n\u2591\u00a0\u00a0\u2588\u2588\u2591\n\u2591\u00a0\u00a0\u2588\u2588\u2591\n\u2591\u00a0\u00a0\u2593\n\u00a0\u00a0\u00a0\u2592\u00a0\u2591\n\u00a0\u00a0\u00a0\u2592\u00a0\u2591\n\u00a0\u2591\n").replaceAll("j", "\ufeff\u00a0\u2584\u2584\u2584\u2588\u2588\u2584\u2584\u2584\u2584\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u2588\u2588\u2593\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\u2588\u2588\u2593\n\u2593\u2588\u2588\u2584\u2588\u2588\u2593\u00a0\u00a0\n\u00a0\u2593\u2588\u2588\u2588\u2592\u00a0\u00a0\u2592\n\u00a0\u2592\u00a0\u00a0\u2593\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2592\u2591\n\u00a0\u2592\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u2591\u00a0\u00a0\u2592\n\u00a0\u2591\u00a0\u2591\u00a0\u2591\n\u00a0\u2591\u00a0\u00a0\u00a0\u2591\n").replaceAll("k", "\ufeff\u00a0\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u2584\u2588\n\u00a0\u2588\u2588\u2584\u2588\u00a0\u2592\n\u2593\u2588\u2588\u2588\u2584\u2592\n\u2593\u2588\u2588\u00a0\u00a0\u00a0\u2588\u2584\n\u2592\u2588\u2588\u2592\u00a0\u00a0\u00a0\u2588\u2584\n\u2592\u00a0\u2592\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u2593\u2592\n\u2591\u00a0\u2591\u00a0\u2592\u00a0\u2592\n\n").replaceAll("l", "\u2593\u2588\u2588\u2593\n\u2593\u2588\u2588\u2592\n\u2592\u2588\u2588\u2591\n\u2592\u2588\u2588\u2591\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2591\u00a0\u00a0\u2593\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2591\u00a0\u2591\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2591\n\u00a0\u00a0\u2591\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n").replaceAll("m", "\ufeff\u00a0\u2588\u2588\u2588\u2584\u00a0\u2584\u2588\u2588\u2588\u2593\n\u2593\u2588\u2588\u2592\u2580\u2588\u2580\u00a0\u2588\u2588\u2592\n\u2593\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\u2591\n\u2592\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u2588\u2588\n\u2592\u2588\u2588\u2592\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2588\u2588\u2592\n\u2591\u00a0\u2592\u2591\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n\u2591\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("n", "\ufeff\u00a0\u2588\u2588\u2588\u2584\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2593\n\u00a0\u2588\u2588\u2593\u00a0\u2588\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2593\n\u2593\u2588\u2588\u2593\u00a0\u2588\u00a0\u00a0\u2588\u2588\u2592\n\u2593\u2588\u2588\u2592\u00a0\u00a0\u2590\u258c\u2588\u2588\u2592\n\u2592\u2588\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\u2592\n\u2591\u00a0\u2592\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u2592\n\u2591\u00a0\u2592\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u2592\u00a0\u2592\n\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u2591\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("o", "\ufeff\u00a0\u2592\u2588\u2588\u2588\u2588\u2588\u00a0\u00a0\u00a0\u00a0\n\u2592\u2588\u2588\u2592\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\n\u2592\u2588\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\n\u2592\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\n\u2591\u00a0\u00a0\u00a0\u2588\u2588\u2588\u2588\u2593\n\u2591\u00a0\u00a0\u00a0\u00a0\u2592\u2591\u2592\u2591\u2592\u2591\u00a0\u00a0\u00a0\n\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u2592\u2591\u00a0\u00a0\u00a0\n\u2591\u00a0\u2591\u00a0\u00a0\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\n\u00a0\u00a0\u00a0\u00a0\u2591\u00a0\u2591\n").replaceAll("p", "\ufeff\u00a0\u2588\u2588\u2593\u2588\u2588\u2588\n\u2593\u2588\u2588\u2591\u00a0\u00a0\u2588\u2588\u2592\n\u2593\u2588\u2588\u2591\u00a0\u2588\u2588\u2593\u2592\n\u2592\u2588\u2588\u2584\u2588\u2593\u2592\u00a0\u2592\n\u2592\u2588\u2588\u2592\u00a0\u2591\u00a0\u00a0\u2591\n\u2592\u2593\u2592\u2591\u00a0\u2591\u00a0\u00a0\u2591\n\u2591\u2592\u00a0\u2591\n\u2591\u2591\n").replaceAll("q", "\ufeff\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2588\u2588\u2588\n\u2592\u2588\u2588\u2593\u00a0\u00a0\u00a0\u2588\u2588\u2593\n\u2592\u2588\u2588\u2592\u00a0\u00a0\u00a0\u2588\u2588\u2592\n\u2592\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\n\u00a0\u00a0\u2592\u2588\u2588\u2588\u2592\u2588\u2584\n\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2592\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("r", "\ufeff\u00a0\ufeff\u00a0\u2588\u2588\u2588\u2588\u2588\n\u2593\u2588\u2588\u00a0\u2592\u2588\u2588\u2593\n\u2593\u2588\u2588\u00a0\u2591\u2584\u2588\u2592\n\u2592\u2588\u2588\u00a0\u2588\u2588\u2584\n\u2592\u2588\u2588\u2593\u00a0\u2592\u2588\u2588\u2592\n\u2591\u2592\u00a0\u2592\u2593\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\n\u00a0\u2592\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2591\u2591\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u2591\n").replaceAll("s", "\ufeff\u00a0\u00a0\u00a0\u2584\u2588\u2588\u2588\u2588\u2584\n\u2592\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2592\u00a0\u00a0\u2593\u2588\u2588\u2584\n\u00a0\u00a0\u2584\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\n\u00a0\u00a0\u2593\u2588\u2588\u2588\u2588\u2588\u2593\n\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2592\u00a0\u00a0\u2592\u2593\n\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u2591\u00a0\u00a0\u2591\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("t", "\u2584\u2584\u2584\u2588\u2588\u2588\u2588\u2588\u2593\n\u2593\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\u00a0\u2593\u2592\n\u2592\u00a0\u00a0\u00a0\u2593\u2588\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2591\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\u2593\n\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u2588\u2588\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u2591\n").replaceAll("u", "\u2588\u2593\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\n\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2593\n\u2593\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u2592\u2588\u2588\n\u2593\u2588\u2588\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u2588\u2588\n\u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2593\n\u2592\u2593\u00a0\u00a0\u00a0\u00a0\u2592\u2592\u00a0\u00a0\u00a0\u2592\n\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\n\n").replaceAll("v", "\u2588\u2588\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2588\n\u2593\u2588\u2592\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2593\n\u00a0\u2593\u2588\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2593\u2592\n\u00a0\u00a0\u00a0\u2592\u2593\u2588\u00a0\u2588\u2592\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u2592\u2580\u00a0\u00a0\u00a0\u2588\n\u00a0\u00a0\u00a0\u2591\u00a0\u2590\u2591\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2592\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("w", "\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\n\u2593\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u2588\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u00a0\u2591\n\u2592\u2588\u2591\u00a0\u00a0\u00a0\u00a0\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\u2588\u2593\u00a0\n\u2592\u2588\u2591\u00a0\u2588\u2592\u2588\u00a0\u00a0\u00a0\u2588\u2592\n\u2591\u2591\u2588\u2588\u2592\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2593\u00a0\n\u2591\u00a0\u2593\u2591\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\u00a0\u00a0\u2592\u00a0\u00a0\n\u00a0\u00a0\u2592\u00a0\u2591\u00a0\u2591\n\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u2591\u00a0\u00a0\u00a0\u2591\n\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("x", "\u2593\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2593\n\u2592\u00a0\u00a0\u2592\u2588\u00a0\u00a0\u00a0\u2588\u2592\n\u2591\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\u2588\u2593\n\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2593\u2588\u2592\n\u2592\u2588\u2588\u2592\u00a0\u2592\u2588\u2588\u2592\n\u2592\u00a0\u00a0\u2593\u2592\u00a0\u2591\u00a0\u00a0\u2593\u2592\u2593\n\u2591\u2591\u00a0\u00a0\u00a0\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2593\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u2591\n\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u2591\n").replaceAll("y", "\u2593\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2593\n\u2593\u2592\u2588\u2588\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\n\u2592\u00a0\u00a0\u2592\u2588\u2588\u00a0\u00a0\u2588\u2588\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2591\u00a0\u2590\u2588\u2588\u2592\u2593\n\u00a0\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\u2593\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2588\u2588\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u2593\u2588\u2588\u00a0\u2591\u2592\n\u00a0\u2592\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u2591\u00a0\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u2591\u00a0\u2591\n").replaceAll("z", "\u2593\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2593\n\u2592\u00a0\u2592\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u2584\u2588\u2592\n\u2591\u00a0\u2592\u00a0\u00a0\u00a0\u00a0\u2584\u2588\u2592\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u2584\u2588\u00a0\u2580\u00a0\u00a0\u00a0\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2591\u2592\u2592\u00a0\u2593\u2591\u00a0\u2592\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u2591\u2591\u2592\u00a0\u00a0\u00a0\u2592\u00a0\u2591\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2592\n\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u2593\n").replaceAll(" ", "\n\n\n\n\n");
        } else if (lower.startsWith("/j ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u4e39").replaceAll("b", "\u4e43").replaceAll("c", "\u531a").replaceAll("d", "\u5200").replaceAll("e", "\u30e2").replaceAll("f", "\u4e0b").replaceAll("g", "\u30e0").replaceAll("h", "\u5344").replaceAll("i", "\u5de5").replaceAll("j", "\uff2a").replaceAll("k", "\uff2b").replaceAll("l", "\u3125").replaceAll("m", "\u722a").replaceAll("n", "\u308c").replaceAll("o", "\u53e3").replaceAll("p", "\u3117").replaceAll("q", "\uff31").replaceAll("r", "\u5c3a").replaceAll("s", "\uff33").replaceAll("t", "\u5315").replaceAll("u", "\u222a").replaceAll("v", "\u2228").replaceAll("w", "\u5c71").replaceAll("x", "\u30e1").replaceAll("y", "\u311a").replaceAll("z", "\u4e59").replaceAll(" ", "   ");
        } else if (lower.startsWith("/o ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u24d0").replaceAll("b", "\u24d1").replaceAll("c", "\u24d2").replaceAll("d", "\u24d3").replaceAll("e", "\u24d4").replaceAll("f", "\u24d5").replaceAll("g", "\u24d6").replaceAll("h", "\u24d7").replaceAll("i", "\u24d8").replaceAll("j", "\u24d9").replaceAll("k", "\u24da").replaceAll("l", "\u24db").replaceAll("m", "\u24dc").replaceAll("n", "\u24dd").replaceAll("o", "\u24de").replaceAll("p", "\u24df").replaceAll("q", "\u24e0").replaceAll("r", "\u24e1").replaceAll("s", "\u24e2").replaceAll("t", "\u24e3").replaceAll("u", "\u24e4").replaceAll("v", "\u24e5").replaceAll("w", "\u24e6").replaceAll("x", "\u24e7").replaceAll("y", "\u24e8").replaceAll("z", "\u24e9").replaceAll("0", "\u3267").replaceAll("1", "\u278a").replaceAll("2", "\u278b").replaceAll("3", "\u278c").replaceAll("4", "\u278d").replaceAll("5", "\u278e").replaceAll("6", "\u278f").replaceAll("7", "\u2790").replaceAll("8", "\u2791").replaceAll("9", "\u2792");
        } else if (lower.startsWith("/r ")) {
            String restOfString = str.substring(str.indexOf(" ") + 1);
            result = restOfString.toLowerCase().replaceAll("a", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2592\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("b", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n").replaceAll("c", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("d", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n").replaceAll("e", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("f", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n").replaceAll("g", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("h", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("i", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("j", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("k", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("l", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("m", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2592\u2592\u2592\u2588\u2588\u2592\n\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2588\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("n", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2588\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2588\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2588\u2588\u2592\n").replaceAll("o", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("p", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n").replaceAll("q", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2588\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n").replaceAll("r", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("s", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("t", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n").replaceAll("u", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("v", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n").replaceAll("w", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2588\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\n\u2592\u2588\u2588\u2592\u2592\u2592\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("x", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2592\u2592\u2588\u2588\u2588\u2592\u2592\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("y", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n").replaceAll("z", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2592\u2592\u2588\u2588\u2588\u2592\u2592\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll(" ", "\n").replaceAll("0", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2588\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2588\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("1", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2588\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("2", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("3", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("4", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("5", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n").replaceAll("6", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("7", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n").replaceAll("8", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replaceAll("9", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n").replace("?", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2592\u2592\n\u2592\u2588\u2592\u2592\u2592\u2592\u2592\u2588\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2592\u2592\u2588\u2592\u2592\u2592\u2592\n").replaceAll("#", "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n\u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2592\n\u2592\u2592\u2588\u2592\u2592\u2592\u2588\u2592\u2592\n");
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
    public String getUrbanDictionaryDef(String words) {
        String url = "http://api.urbandictionary.com/v0/define?term=" +
                words.trim().replaceAll("\\s+", "+");
        WebCrawler reader = new WebCrawler(url);
        String def;
        try {
            JSONArray jsonArray = reader.getJsonFromHtml().getJSONArray("list");
            int random = new Random().nextInt(jsonArray.length());
            def = jsonArray.getJSONObject(random).get("definition").toString();

            return "/ud " + words + "\n\n" + def;
        } catch (org.json.JSONException e) {
            return "No definition found.";
        }
    }

    // uses WebCrawler.java to parse the Json text from the wikipedia API
    // Then gets the definition and returns it as text
    public String getWikiSearch(String words) {
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

    public String getMerianDefinition(String word){
            String str = Dictionary.searchOnlineWord(word);

            if(str.toLowerCase().trim().equals("error"))
                return "No definition found for "+word+".";
            else
                return "/dict " + word + "\n\n" + (str.trim());
    }

    public String getSongLyrics(String artist, String songName){
        AZLyrics az = new AZLyrics(artist, songName);
        try {
            return az.getSongName() + " by " + az.getArtist() + ":\n\n" + az.getLyrics();
        }catch ( Exception e){
            return "No lyrics found for "+songName+" by "+artist+".";
        }
    }

    private String removeCall(String lower){
        if(lower.contains("@bittle_bot")) {
            int indexBefore = lower.indexOf("@bittle_bot") - 1;

            if(indexBefore >=0){
                if(lower.charAt(indexBefore) != ' '){
                    lower = lower.replaceAll("@bittle_bot", "");
                }
            }

        }
        return lower;
    }
}
