package com.bittle.telegram.text;

import com.bittle.telegram.main.MainClass;

import java.util.HashMap;
import java.util.Map;

// holds string commands => input, output
public class StringCommands {
    private Map<String, String> setCommands = new HashMap<>();

    public StringCommands() {
        // commands that are set on stone, just leave them here to avoid cluster on
        // other classes
        setCommands.put("/commands", "Commands:\n\n/bittle\n/info\n/status\n/options\n/text" +
                "\n/games\n/joke\n/roast <name>\n/mock <text>\n" +
                "/ly <artist> <song name>\n/py <python script>\n/math <equation>\n" +
                "/ud <text> [urban dictionary]\n/dict <word> [Meriam-Webster]\n/wiki <text> [Wikipedia]");

        setCommands.put("/bittle", "Bittle is my creator! You can chat and ask questions at\n\nt.me/OGBittle");
        setCommands.put("/options", "The following are bot options:\n\nTo start or stop games:\n/games on\n" +
                "/games off\n\nTo set max text length:\n/maxtext <number>" +
                "\n\nTo allow/disallow other bots in chat:\n/antibot on\n/antibot off" +
                "\n\nTo allow/disallow members to join without profile pictures:\n/pfp on\n/pfp off" +
                "\n\nTo allow/disallow bot to greet new members:\n/welcome off\n/welcome on" +
                "\n\nTo allow/disallow bot to say farewell to members:\n/bye off\n/bye on" +
                "\n\nTo set greeting of new members " +
                "(type _user_ where you want the username to go):\n/setwelcome <text>" +
                "\n\nTo set farewell message " +
                "(type _user_ where you want the username to go):\n/setbye <text>");

        setCommands.put("/text", "text commands:\n\n/say <text>\n/lower <text>\n/caps <text>\n" +
                "/c <text>\n/f <text>\n/g <text>\n/j <text>\n/o <text>\n/u <text>");

        setCommands.put("/math", "Solve math equations!\n\nExample:\n!math 3+4+8*(pi*sin(60))+3");

        setCommands.put("/games", "games:\n\n/type\n/scramble\n/taboo\n/brick\n/hangman\n\nTo see game rules " +
                "type info after the game style, like so:\n\n/type info");

        setCommands.put("/ly", "Get song lyrics!\n\nExample:\n\n/ly Eminem_Beautiful\n\nMust contain _ " +
                "to separate artist from song name");

        setCommands.put("/dict", "Get a definition from a professional online dictionary!\n\nExample:\n\n/dict hello");
        setCommands.put("/scores", "To get game scores send:\n\n/scores type\n/scores scramble\n" +
                "/scores taboo\n/scores brick\n/scores hangman\n\n" +
                "Or do\n\n/scores all\n\nTo get all scores in private message.");
        setCommands.put("/type info", "Type game info: \n\nTo play send /type in any group, " +
                "first person to type the given word wins! To get scores do\n\n/scores type");

        setCommands.put("/scramble info", "Scramble game info: \n\nTo play send /scramble in any group, " +
                "first person to unscramble the given scrambled word wins! To get scores do\n\n/scores scramble");

        setCommands.put("/taboo info", "Taboo game info: \n\nTo play send /taboo in any group, " +
                "a word will be sent to the person who sent /taboo. That person will " +
                "gives clues to the group about that word, but if the person accidentally mentions " +
                "the word the game is over. To get scores do\n\n/scores taboo");

        setCommands.put("/brick info", "Brick game info: \n\nTo play send /brick in any group, " +
                "a brick will be added to the wall! Keep building to keep " +
                "unwanted threats out!");

        setCommands.put("/hangman info", "Hangman game info: \n\nTo play send /hangman in any group, " +
                "guess the word, or guess character by character until the game finishes." +
                " Be aware that you have only 7 tries! To guess a character simply send a message" +
                " with only one character.");

        setCommands.put("kitty", "Meow!");

        setCommands.put("/ud", "Get a definition from urban dictionary!\n\nExample:\n\n/ud bittle");

        setCommands.put("/status", MainClass.PER_GROUP_BOT_CONFIG.getSettings());

        setCommands.put("/welcome", "Welcome message is " +
                (MainClass.PER_GROUP_BOT_CONFIG.isWelcomeOn() ? "on" : "off") + ".\n\nWelcome message: \n" +
                MainClass.PER_GROUP_BOT_CONFIG.getWelcomeMessage() + "\n\nTo set welcome message:\n/setwelcome <text>");

        setCommands.put("/bye", "Bye message is " + (MainClass.PER_GROUP_BOT_CONFIG.isByeOn() ? "on" : "off")
                + ".\n\nBye message: \n" + MainClass.PER_GROUP_BOT_CONFIG.getByeMessage() +
                "\n\nTo set welcome message:\n/setwelcome <text>");

    }

    public String get(String input) {
        return setCommands.getOrDefault(input, "");
    }
}
