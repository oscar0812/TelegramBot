package com.bittle.telegram.Main;

import com.bittle.telegram.Games.Hangman;
import com.bittle.telegram.Games.ScoreKeeper;
import com.bittle.telegram.ImageModifier;
import com.bittle.telegram.ScriptRunner;
import com.bittle.telegram.Text.Dictionary;
import com.bittle.telegram.Text.StringUtils;
import org.telegram.telegrambots.api.methods.groupadministration.*;
import org.telegram.telegrambots.api.objects.*;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oscartorres on 6/30/17.
 */

public class BotMaintainer extends MainBotClass {

    public class CurrentGame {
        private String current_type_word = "";
        private String current_unscrambled_word = "";
        private String current_scramble_word = "";
        private String current_taboo_word = "";
        private String player_username = "";

        private long group_id;

        private boolean is_type = false;
        private boolean is_scramble = false;
        private boolean is_taboo = false;

        private Hangman hangman = null;

        private CurrentGame(long g_id) {
            group_id = g_id;
        }
    }

    private HashMap<Long, CurrentGame> currentGames = new HashMap<Long, CurrentGame>();

    private final BotResponse botResponse = new BotResponse();
    private final Dictionary dictionary = new Dictionary();

    ScoreKeeper games;


    // respond appropriate for the incoming update
    public void respondToText(Update update, ScoreKeeper g) {

        games = g;
        Message message = update.getMessage();
        long chat_id = message.getChatId();
        String message_text = message.getText();
        message_text = removeCall(message_text);
        String message_text_lower = message_text.toLowerCase();
        User message_sender = message.getFrom();

        checkIfSettingCommand(update);

        if (message_text.length() > MainClass.perGroupBotConfig.getMaxTextLength()
                && !userIsAdmin(chat_id, message_sender)) {

            sendTextMessage(chat_id, "Message text too long! Limit: " +
                    MainClass.perGroupBotConfig.getMaxTextLength() + "\nYour message length: " + message_text.length());

            // kick for long message
            kickUser(chat_id, message_sender);
        }

        if (!MainClass.perGroupBotConfig.isBotOn()) {
            // exit the function if bot is off
            if (isAdminOfBot(message_sender) && message_text_lower.equals("/check")) {
                sendTextMessage(chat_id, "Bot is off.");
            }

            return;
        }

        CurrentGame current = null;

        if (currentGames.containsKey(chat_id))
            current = currentGames.get(chat_id);

        String bot_say_this = gameCommand(update, current); // check if its a game command

        if (message_text_lower.equals("/reset")) {
            if (isAdminOfBot(message_sender)) {
                if (currentGames.containsKey(chat_id)) {
                    CurrentGame game = currentGames.get(chat_id);
                    String all = "";
                    if (!game.current_type_word.isEmpty()) {
                        all += "Type: " + game.current_type_word + "\n\n";
                    }
                    if (!game.current_unscrambled_word.isEmpty()) {
                        all += "Scramble answer: " + game.current_unscrambled_word + "\n\n";
                    }
                    if (!game.current_taboo_word.isEmpty()) {
                        all += "Taboo answer: " + game.current_taboo_word + "\n\n";
                    }

                    if (game.hangman != null) {
                        all += "Hangman answer: " + game.hangman.getCorrectAnswer() + "\n\n";
                        game.hangman = null;
                    }

                    if (!all.trim().isEmpty())
                        bot_say_this = "Games cleared:\n\n" + all.trim();
                    else
                        bot_say_this = "Nothing to reset.";
                    currentGames.remove(chat_id);
                } else {
                    bot_say_this = "Nothing to reset.";

                }
            }

        } else if (message_text_lower.startsWith("/scores ")) {
            String restOfText = message_text_lower.substring(message_text_lower.indexOf(" ") + 1);
            restOfText = restOfText.trim();
            if (restOfText.equals("type")) {
                bot_say_this = games.getScores_type();
            } else if (restOfText.equals("scramble")) {
                bot_say_this = games.getScores_scramble();

            } else if (restOfText.equals("taboo")) {
                bot_say_this = games.getScores_taboo();

            } else if (restOfText.equals("brick")) {
                bot_say_this = games.getScores_brick() + "";

            } else if (restOfText.equals("hangman")) {
                bot_say_this = games.getScores_hangman();

            } else if (restOfText.equals("all")) {
                // to avoid spam
                sendTextMessage(message_sender.getId(), games.getScores_all());
                bot_say_this = "Scores sent to " + message_sender.getUserName() + " in private message to avoid spam.";
            }

        } else if (message_text_lower.startsWith("/admin ") && isBotCreator(message_sender)) {
            String rest = message_text.substring(message_text.indexOf(" ") + 1).trim();
            try {
                int user_id = Integer.parseInt(rest);
                String username = getUsername(chat_id, user_id);

                if (username.equals("null")) {
                    sendTextMessage(chat_id, "Can\'t admin a user with no username.");
                } else {
                    if (MainClass.GLOBAL_BOT_CONFIG.adminExists(user_id)) {
                        sendTextMessage(chat_id, username + " is already a bot admin.");
                    } else {
                        MainClass.GLOBAL_BOT_CONFIG.addNewAdmin(user_id, username);
                        sendTextMessage(chat_id, username + " has been given bot admin privileges.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((message_text_lower.startsWith("/rem ") || message_text_lower.startsWith("/remove"))
                && isBotCreator(message_sender)) {
            String rest = message_text.substring(message_text.indexOf(" ") + 1).trim();

            try {
                Integer user_id = Integer.parseInt(rest);

                if (!MainClass.GLOBAL_BOT_CONFIG.adminExists(user_id)) {
                    sendTextMessage(chat_id, "No admin by id " + user_id);
                } else {
                    MainClass.GLOBAL_BOT_CONFIG.removeAdmin(user_id);
                    sendTextMessage(chat_id, getUsername(chat_id, user_id) + " is no longer a bot admin.");
                }

            } catch (Exception e) {
                // not a long in a string
                if (!MainClass.GLOBAL_BOT_CONFIG.adminExists(rest)) {
                    sendTextMessage(chat_id, "No admin with username of " + rest);
                } else {
                    MainClass.GLOBAL_BOT_CONFIG.removeAdmin(rest);
                    sendTextMessage(chat_id, rest + " is no longer a bot admin.");
                }
            }
        } else if(message_text_lower.equals("/dev") && isBotCreator(message_sender)){
            bot_say_this = "Dev commands:\n\n(To add a bot admin)\n/admin <user id>\n\n" +
                    "(To remove a current bot admin)\n/remove <user id>\n/remove <user username>" +
                    "\n(Or replace /remove with /rem)\n\n(To stop the bot completely)\n/exit";

        } else if (message_text_lower.equals("/exit") && isBotCreator(message_sender)) {
            sendTextMessage(chat_id, "Shutting down..");
            System.out.println("Bot shut down.");
            System.exit(0);

        } else if (message_text_lower.equals("/leave") && isAdminOfBot(message_sender)) {

            LeaveChat leaveChat = new LeaveChat()
                    .setChatId(chat_id);

            try {
                sendTextMessage(chat_id, "Ok, bye.");
                this.leaveChat(leaveChat);
            } catch (Exception e) {
                System.out.println("ERROR LEAVING CHAT\nID: " + chat_id);
            }

        } else if (message_text_lower.equals("/pfp")) {
            try {
                sendPfp(chat_id, message_sender);

            } catch (Exception e) {
                System.out.println("ERROR TRYING TO GET PFP:\n" + e.toString());
            }
        } else if (message_text_lower.equals("/admins")) {
            try {
                GetChatAdministrators chatAdministrators = new GetChatAdministrators();
                chatAdministrators.setChatId(chat_id);
                List<ChatMember> administrators = getChatAdministrators(chatAdministrators);

                String all_str = "Group admins: \n";
                for (int x = 0; x < administrators.size(); x++) {
                    ChatMember member = administrators.get(x);
                    all_str += ("@" + member.getUser().getUserName() + "\n");
                }
                bot_say_this = all_str.trim() + "\n\nBot admins:\n" + MainClass.GLOBAL_BOT_CONFIG.getAdmins();

            } catch (Exception e) {
                System.out.println("ERROR GETTING ADMINS:\n" + e.toString());
            }

        } else if (message_text_lower.equals("/id")) {
            bot_say_this = message_sender.getId() + "";
        } else if (message_text_lower.equals("/info")) {
            // get the info of the person who sent !info
            String user_first_name = update.getMessage().getFrom().getFirstName();
            String user_last_name = update.getMessage().getFrom().getLastName();
            String user_username = update.getMessage().getFrom().getUserName();
            long user_id = update.getMessage().getFrom().getId();

            bot_say_this = "First name: " + user_first_name
                    + "\nLast name: " + user_last_name
                    + "\nUsername: " + user_username
                    + "\nID: " + user_id;
        } else if (message_text_lower.startsWith("/py ") && MainClass.perGroupBotConfig.isPyCommandOn()) {
            String script = message_text.substring(message_text.indexOf(" ") + 1).trim();
            //  "print(\"print this!\")"
            bot_say_this = ScriptRunner.python(script);

        } else if (message_text_lower.startsWith("/mock ")) {
            String restOfString = message_text.substring(message_text.indexOf(" ") + 1).
                    trim().replaceAll("\\s+", " ");

            new Thread(() -> {
                BufferedImage image = ImageModifier.mockImage(restOfString);
                InputStream inputStream = ImageModifier.bufferedImageToInputStream(image);

                sendPhotoMessage("spongebob", inputStream, chat_id);
                log(update, "Mock Picture Sent.");
            }).start();


        } else if (message_text_lower.startsWith("/gn ")) {
            try {
                String restOfText = message_text.substring(message_text.indexOf(" ") + 1);

                SetChatTitle title = new SetChatTitle().setChatId(chat_id).setTitle(restOfText);

                if (setChatTitle(title)) {
                    sendTextMessage(chat_id, "Set chat title");
                } else {
                    sendTextMessage(chat_id, "Couldn\'t set chat title");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (bot_say_this.isEmpty()) {
            // if still empty, check if its a regular text command
            bot_say_this = botResponse.getTextResponse(update);
        }

        if (!bot_say_this.isEmpty()) {
            sendTextMessage(chat_id, bot_say_this);
        }

        // too many checks, so hangman has its own method
        hangmanGame(update);

        log(update, bot_say_this);
    }

    // check if its a command for a game
    private String gameCommand(Update update, CurrentGame current) {

        Message message = update.getMessage();
        long chat_id = message.getChatId();
        String message_text = message.getText();
        message_text = removeCall(message_text);
        String message_text_lower = message_text.toLowerCase();
        User message_sender = message.getFrom();


        if (!MainClass.perGroupBotConfig.areGamesOn()) {
            return "";
        }

        String bot_say_this = "";

        // type game win
        if (current != null && current.is_type && message_text_lower.equals(current.current_type_word)) {
            String username = message_sender.getUserName();
            games.addToScore_type(username, 1);
            current.is_type = false;
            current.current_type_word = "";
            sendTextMessage(chat_id, username + " has won!");
        }

        // scramble game win
        if (current != null && current.is_scramble && message_text_lower.equals(current.current_unscrambled_word)) {
            String username = message_sender.getUserName();
            games.addToScore_scramble(username, 1);
            current.is_scramble = false;
            current.current_scramble_word = "";
            current.current_unscrambled_word = "";
            sendTextMessage(chat_id, username + " has won!");
        }

        // taboo game cheat
        if (current != null && current.is_taboo && message_text_lower.contains(current.current_taboo_word)
                && message_sender.getUserName().equals(current.player_username)) {
            String username = message_sender.getUserName();
            sendTextMessage(chat_id, username + " is a cheater!\n\n" + current.current_taboo_word + " was the word.");
            current.is_taboo = false;
            current.current_taboo_word = "";
        }

        // taboo game win
        else if (current != null && current.is_taboo && message_text_lower.contains(current.current_taboo_word)) {
            String username = message_sender.getUserName();
            games.addToScore_taboo(username, 1);
            current.is_taboo = false;
            current.current_taboo_word = "";
            sendTextMessage(chat_id, username + " has won!");
        }

        if (message_text_lower.equals("/type")) {

            if (currentGames.containsKey(chat_id)) {
                CurrentGame game = currentGames.get(chat_id);
                if (game.current_type_word.isEmpty()) {
                    game.current_type_word = dictionary.getRandomWord();
                    game.is_type = true;
                }

                bot_say_this = "Type: " + currentGames.get(chat_id).current_type_word;
            } else {
                CurrentGame game = new CurrentGame(chat_id);
                game.current_type_word = dictionary.getRandomWord();
                game.is_type = true;
                currentGames.put(chat_id, game);

                bot_say_this = "Type: " + game.current_type_word;
            }

        } else if (message_text_lower.equals("/scramble")) {
            if (currentGames.containsKey(chat_id)) {
                CurrentGame game = currentGames.get(chat_id);
                if (game.current_scramble_word.isEmpty()) {
                    game.current_unscrambled_word = dictionary.getRandomWord();
                    game.current_scramble_word = dictionary.scrambleWord(game.current_unscrambled_word);
                    game.is_scramble = true;
                }

                bot_say_this = "Unscramble: " + game.current_scramble_word;
            } else {
                CurrentGame game = new CurrentGame(chat_id);
                game.current_unscrambled_word = dictionary.getRandomWord();
                game.current_scramble_word = dictionary.scrambleWord(game.current_unscrambled_word);
                game.is_scramble = true;
                currentGames.put(chat_id, game);

                bot_say_this = "Unscramble: " + game.current_scramble_word;
            }
        } else if (message_text_lower.equals("/taboo")) {
            if (!message.getChat().isUserChat()) {
                if (!currentGames.containsKey(chat_id)) {
                    currentGames.put(chat_id, new CurrentGame(chat_id));
                }
                CurrentGame game = currentGames.get(chat_id);
                if (!game.current_taboo_word.isEmpty()) {
                    sendTextMessage(chat_id, game.player_username + " has the word.");
                } else {
                    game.current_taboo_word = dictionary.getRandomWord();
                    game.player_username = message_sender.getUserName();
                    game.is_taboo = true;
                    sendTextMessage(message_sender.getId(), "Word is " + game.current_taboo_word);
                    bot_say_this = ("Word sent to " + message_sender.getUserName());
                }
            } else {
                bot_say_this = "Taboo must be played in group chats.";
            }
        } else if (message_text_lower.equals("/brick")) {
            games.addToScore_brick();
            int bricks = games.getScores_brick();
            if (bricks % 10 == 0 && bricks > 0)
                sendTextMessage(chat_id, "The wall just got 10 feet taller!");
            bot_say_this = bricks + " bricks on the wall! Keep building!";

        }

        return bot_say_this;
    }

    // takes care of hangman game
    private void hangmanGame(Update update) {
        String str = update.getMessage().getText().trim().toLowerCase();
        String username = update.getMessage().getFrom().getUserName();
        long chat_id = update.getMessage().getChatId();

        if (!currentGames.containsKey(chat_id)) {
            currentGames.put(chat_id, new CurrentGame(chat_id));
        }

        CurrentGame currentGame = currentGames.get(chat_id);

        String bot_say_this = "";

        if (str.equals("/hangman")) {
            if (currentGame.hangman == null || currentGame.hangman.isGameOver())
                currentGame.hangman = new Hangman(new Dictionary().getRandomWord());

            sendTextMessage(chat_id, currentGame.hangman.getDrawing() + "\n\n" +
                    currentGame.hangman.getCurrentString() + "\n\nGuesses: " + currentGame.hangman.getAllGuesses());

        } else if (currentGame.hangman != null) {
            if (str.equals(currentGame.hangman.getCorrectAnswer()) || currentGame.hangman.isWin()) {
                bot_say_this = "Correct answer! Word was " + currentGame.hangman.getCorrectAnswer() +
                        "\n\nWinner: " + username;

                games.addToScore_hangman(username, 1);
                currentGame.hangman = null;
            }

            if ((str.length() == 1 && currentGame.hangman != null) && (!StringUtils.containsEmoji(str))
                    && StringUtils.isAlpha(str.charAt(0))) {

                char c = str.charAt(0);
                if (currentGame.hangman.isAlreadyGuessed(c)) {
                    bot_say_this = c + " has already been guessed.\n\nGuesses: " + currentGame.hangman.getAllGuesses();
                } else {
                    // new char
                    int before_wrong = currentGame.hangman.getCurrentWrong();
                    currentGame.hangman.guess(c);
                    int after_wrong = currentGame.hangman.getCurrentWrong();

                    if (before_wrong == after_wrong) {
                        // guessed a correct character

                        if (currentGame.hangman.isWin()) {
                            games.addToScore_type(username, 1);
                            bot_say_this = "Game won! Word was " + currentGame.hangman.getCorrectAnswer() +
                                    "\n\nWinner: " + username;

                            currentGame.hangman = null;
                        } else
                            bot_say_this = currentGame.hangman.getDrawing() + "\n\n" +
                                    currentGame.hangman.getCurrentString() + "\n\nGuesses: "
                                    + currentGame.hangman.getAllGuesses();

                    } else {
                        // guessed wrong char
                        System.out.println("Wrong guess!");

                        if (currentGame.hangman.isGameOver()) {
                            bot_say_this = currentGame.hangman.getDrawing() +
                                    "\n\nGame over. Word was " + currentGame.hangman.getCorrectAnswer();

                            currentGame.hangman = null;
                        } else
                            bot_say_this = currentGame.hangman.getDrawing() + "\n\n" +
                                    currentGame.hangman.getCurrentString() +
                                    "\n\nGuesses: " + currentGame.hangman.getAllGuesses();
                    }
                }
            }

            if (!bot_say_this.isEmpty())
                sendTextMessage(chat_id, bot_say_this);
            log(update, bot_say_this);
        }
    }

    private String removeCall(String lower) {
        if (lower.contains("@bittle_bot")) {
            int indexBefore = lower.indexOf("@bittle_bot") - 1;

            if (indexBefore >= 0) {
                if (lower.charAt(indexBefore) != ' ') {
                    lower = lower.replaceAll("@bittle_bot", "");
                }
            }

        }
        return lower;
    }

    private void checkIfSettingCommand(Update update) {
        String message_text = update.getMessage().getText();
        String message_text_lower = message_text.toLowerCase();
        User message_sender = update.getMessage().getFrom();
        long chat_id = update.getMessage().getChatId();

        if (message_text_lower.equals("/off") && isAdminOfBot(message_sender) && MainClass.perGroupBotConfig.isBotOn()) {
            sendTextMessage(chat_id, "Bot turned off.");
            MainClass.perGroupBotConfig.setBotOn(false);
        } else if (message_text_lower.equals("/on") && !MainClass.perGroupBotConfig.isBotOn()) {
            sendTextMessage(chat_id, "Bot turned on.");
            MainClass.perGroupBotConfig.setBotOn(true);

        } else if (message_text_lower.equals("/games off") && MainClass.perGroupBotConfig.areGamesOn()) {
            sendTextMessage(chat_id, "Games turned off.");
            MainClass.perGroupBotConfig.setGamesOn(false);

        } else if (message_text_lower.equals("/games on") && !MainClass.perGroupBotConfig.areGamesOn()) {
            sendTextMessage(chat_id, "Games turned on.");
            MainClass.perGroupBotConfig.setGamesOn(true);

        } else if (message_text_lower.equals("/py off") && MainClass.perGroupBotConfig.isPyCommandOn()
                && isAdminOfBot(message_sender)) {
            sendTextMessage(chat_id, "Py command turned off.");
            MainClass.perGroupBotConfig.setPyCommandOn(false);

        } else if (message_text_lower.equals("/py on") && !MainClass.perGroupBotConfig.isPyCommandOn()
                && isAdminOfBot(message_sender)) {
            sendTextMessage(chat_id, "Py command turned on.");
            MainClass.perGroupBotConfig.setPyCommandOn(true);

        } else if (message_text_lower.equals("/welcome off") && MainClass.perGroupBotConfig.isWelcomeOn()) {
            sendTextMessage(chat_id, "New users will not be greeted.");
            MainClass.perGroupBotConfig.setWelcomeMessageOn(false);

        } else if (message_text_lower.equals("/welcome on") && !MainClass.perGroupBotConfig.isWelcomeOn()) {
            sendTextMessage(chat_id, "New users will be greeted.");
            MainClass.perGroupBotConfig.setWelcomeMessageOn(true);

        } else if (message_text_lower.equals("/bye off") && MainClass.perGroupBotConfig.isByeOn()) {
            sendTextMessage(chat_id, "Members will not be given a warm bye.");
            MainClass.perGroupBotConfig.setByeMessageOn(false);
        } else if (message_text_lower.equals("/bye on") && !MainClass.perGroupBotConfig.isByeOn()) {
            sendTextMessage(chat_id, "Members will be given a warm bye.");
            MainClass.perGroupBotConfig.setByeMessageOn(true);
        } else if (message_text_lower.equals("/antibot off") && MainClass.perGroupBotConfig.isAntiBotOn()) {
            sendTextMessage(chat_id, "Anti-Bot turned off, bots allowed in this chat.");
            MainClass.perGroupBotConfig.setAntiBotOn(false);

        } else if (message_text_lower.equals("/antibot on") && !MainClass.perGroupBotConfig.isAntiBotOn()) {
            if (!botIsAdmin(update)) {
                sendTextMessage(chat_id, "Anti-Bot only works if this bot is admin.");
            } else {
                sendTextMessage(chat_id, "Anti-Bot turned on, bots not allowed in this chat.");
                MainClass.perGroupBotConfig.setAntiBotOn(true);
            }

        } else if (message_text_lower.equals("/pfp off") && MainClass.perGroupBotConfig.needsPfp()) {
            sendTextMessage(chat_id, "Profile pictures not needed from new members when joining.");
            MainClass.perGroupBotConfig.setPfpNeeded(false);

        } else if (message_text_lower.equals("/pfp on") && !MainClass.perGroupBotConfig.needsPfp()) {
            if (!botIsAdmin(update)) {
                sendTextMessage(chat_id, "Pfp command only works if this bot is admin.");
            } else {
                sendTextMessage(chat_id, "Profile pictures needed from new members when joining.");
                MainClass.perGroupBotConfig.setPfpNeeded(true);
            }

        } else if (message_text_lower.startsWith("/setwelcome ")) {
            String restOfText = message_text.substring(message_text.indexOf(" "));
            MainClass.perGroupBotConfig.setWelcomeMessage(restOfText);
            sendTextMessage(chat_id, "Welcome message set.");

        } else if (message_text_lower.startsWith("/setbye ")) {
            String restOfText = message_text.substring(message_text.indexOf(" "));
            MainClass.perGroupBotConfig.setByeMessage(restOfText);
            sendTextMessage(chat_id, "Bye message set.");

        } else if (message_text_lower.startsWith("/maxtext ")) {
            String restOfText = message_text.substring(message_text.indexOf(" ")).trim();
            try {
                int num = Integer.parseInt(restOfText);
                if (num == 0 || num > 4085) {
                    sendTextMessage(chat_id, "Max message length should be between 1 and 4085");
                } else {
                    sendTextMessage(chat_id, "Max message length set to " + num);
                    MainClass.perGroupBotConfig.setMaxTextLength(num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}