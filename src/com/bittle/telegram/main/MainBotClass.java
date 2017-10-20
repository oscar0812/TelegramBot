package com.bittle.telegram.main;

import com.bittle.telegram.config.PerGroupBotConfig;
import com.bittle.telegram.games.ScoreKeeper;
import com.bittle.telegram.text.StringUtils;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainBotClass extends TelegramLongPollingBot {

    public MainBotClass() {
        super();
    }

    public MainBotClass(BotMaintainer bm) {
        botMaintainer = bm;
    }

    public ScoreKeeper scoreKeeper;

    private BotMaintainer botMaintainer = null;

    @Override
    public void onUpdateReceived(Update update) {
        MainClass.perGroupBotConfig = new PerGroupBotConfig(update);

        // We check if the update has a message and the message has text
        if (update.hasMessage()) {
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().hasText()) {

                scoreKeeper = new ScoreKeeper(update);
                botMaintainer.respondToText(update, scoreKeeper);

            } else if (update.getMessage().hasPhoto()) {
                // photo stuff

            } else if (hasSticker(update)) {


            } else {

                // chat members joined
                if (memberJoinedChat(update)) {

                    List<User> join_users = update.getMessage().getNewChatMembers();
                    if (MainClass.perGroupBotConfig.isWelcomeOn()) {

                        for (int x = 0; x < join_users.size(); x++) {
                            User currentUser = join_users.get(x);

                            if (isBot(currentUser)) {

                                if (MainClass.perGroupBotConfig.isAntiBotOn()) {

                                    if (botIsAdmin(update)) {

                                        sendTextMessage(chat_id, "Anti-bot on.");
                                        kickUser(chat_id, currentUser);
                                        join_users.remove(currentUser);
                                        continue;
                                    } else {
                                        sendTextMessage(chat_id, "Anti bot is on, " +
                                                "but can\'t remove " + currentUser.getUserName() +
                                                " because this bot is not admin.");
                                    }
                                }
                            }

                            if (!userHasPfp(currentUser)) {
                                // joined user doesn't have a pfp
                                if (MainClass.perGroupBotConfig.needsPfp()) {
                                    if (botIsAdmin(update)) {
                                        sendTextMessage(chat_id, "Profile picture needed when joining.");
                                        kickUser(chat_id, currentUser);
                                        join_users.remove(currentUser);
                                    } else {
                                        sendTextMessage(chat_id, "Pfp command is on, " +
                                                "but can\'t remove " + currentUser.getUserName() +
                                                " because this bot is not admin.");
                                    }
                                }
                            }
                        }

                        if (!join_users.isEmpty())
                            sendTextMessage(chat_id, MainClass.perGroupBotConfig.getWelcomeMessage(join_users));
                    }
                }

                // chat member left
                if (memberLeftChat(update) && MainClass.perGroupBotConfig.isByeOn()) {
                    sendTextMessage(chat_id, MainClass.perGroupBotConfig.getByeMessage(update));
                }

            }
        }
    }

    public boolean isBotCreator(User user) {
        String name = user.getUserName();
        return name.equals("OGBittle");
    }

    // checks if in pm of OGBittle
    public boolean isBotCreatorPm(Update up) {
        String user = up.getMessage().getFrom().getUserName();
        Message message = up.getMessage();
        return user.equals("OGBittle") && message.getChat().isUserChat()
                && !message.getChat().isGroupChat();
    }

    // checks if bot admin ( the ones with /leave power, etc)
    public boolean isAdminOfBot(User user) {
        return isBotCreator(user) || MainClass.GLOBAL_BOT_CONFIG.adminExists(user.getId());
    }

    public void sendTextMessage(long chat_id, String message_text) {
        String[] strings = StringUtils.splitString(message_text, Constants.MAX_MESSAGE_INT);

        for (int x = 0; x < strings.length; x++) {
            sendTextMessageHelper(chat_id, strings[x]);
        }
    }

    // sends a text message back to the group/person
    private void sendTextMessageHelper(long chat_id, String message_text) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(message_text);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // 1

    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.hasFilePath()) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::getFile method.
                File file = getFile(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }

    // 2
    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 3
    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a method overload)
        sendPhotoRequest.setNewPhoto(new java.io.File(filePath));
        try {
            // Execute the method
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean userHasPfp(User user) {
        try {
            GetUserProfilePhotos pfp = new GetUserProfilePhotos();
            pfp.setUserId(user.getId());
            pfp.setLimit(4);
            pfp.setOffset(0);

            getUserProfilePhotos(pfp).getPhotos().listIterator(0).next();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public void sendPfp(long chat_id, User user) {
        try {
            GetUserProfilePhotos pfp = new GetUserProfilePhotos();
            pfp.setUserId(user.getId());
            pfp.setLimit(4);
            pfp.setOffset(0);

            List<PhotoSize> photos = getUserProfilePhotos(pfp).getPhotos().listIterator(0).next();

            String f_id = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst().orElse(null).getFileId();

            sendPhotoMessage(chat_id, f_id);
        } catch (Exception e) {
            sendTextMessage(chat_id, "No pfp");
        }
    }

    public void sendPhotoMessage(long chat_id, PhotoSize photoSize) {
        try {

            java.io.File file = downloadPhotoByFilePath(getFilePath(photoSize));
            sendImageUploadingAFile(file.getAbsolutePath(), chat_id + "");
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPhotoMessage(long chat_id, String photo_id) {
        SendPhoto msg = new SendPhoto()
                .setPhoto(photo_id)
                .setChatId(chat_id);
        try {
            sendPhoto(msg);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void sendPhotoMessage(String photoName, InputStream inputStream, long chat_id) {
        SendPhoto msg = new SendPhoto()
                .setNewPhoto(photoName, inputStream)
                .setChatId(chat_id);
        try {
            sendPhoto(msg);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void log(Update update, String botResponse) {
        if (isBotCreatorPm(update)) {
            // don\'t log my pm
            return;
        }
        Message message = update.getMessage();
        long chat_id = message.getChatId();
        String message_text = message.getText();
        User message_sender = message.getFrom();

        String info_from_tag = (message.getChat().isUserChat() ? "[Private Message -> " + message_sender.getUserName() + "]" :
                ("[Group Message {" + (message.getChat().getUserName() != null ? message.getChat().getUserName() :
                        message.getChat().getTitle()) + "} -> " + message_sender.getUserName() + "]"));

        botResponse = botResponse.isEmpty() ? ("") : ("\nBot said: " + botResponse);

        System.out.println("\n[Chat ID: " + chat_id + "]\t" + info_from_tag + "\n" + message_sender.getUserName() + " said: " +
                message_text + botResponse + "\n=====================================================");
    }

    public boolean memberJoinedChat(Update update) {
        List<User> join_users = update.getMessage().getNewChatMembers();
        return (join_users != null && !join_users.isEmpty());
    }

    public boolean memberLeftChat(Update update) {
        User left_user = update.getMessage().getLeftChatMember();
        return left_user != null;
    }

    public String getUsername(long chat_id, Integer user_id) {
        try {
            User user = (getChatMember(new GetChatMember()
                    .setChatId(chat_id).setUserId(user_id))).getUser();

            return user.getUserName();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public boolean isBot(User user) {
        return user.getUserName().endsWith("bot");
    }

    public boolean userIsAdmin(long chat_id, User user) {
        try {
            GetChatAdministrators chatAdministrators = new GetChatAdministrators();
            chatAdministrators.setChatId(chat_id);
            List<ChatMember> administrators = getChatAdministrators(chatAdministrators);

            boolean is_admin = false;
            for (int x = 0; x < administrators.size(); x++) {
                if (administrators.get(x).getUser().getUserName().equals(user.getUserName())) {
                    is_admin = true;
                    break;
                }
            }

            return is_admin;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean botIsAdmin(Update update) {
        long chat_id = update.getMessage().getChatId();
        try {
            GetChatAdministrators chatAdministrators = new GetChatAdministrators();
            chatAdministrators.setChatId(chat_id);
            List<ChatMember> administrators = getChatAdministrators(chatAdministrators);

            boolean bot_is_admin = false;
            for (int x = 0; x < administrators.size(); x++) {
                if (administrators.get(x).getUser().getUserName().equals(getBotUsername())) {
                    bot_is_admin = true;
                    break;
                }
            }

            return bot_is_admin;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasSticker(Update update) {
        return update.hasMessage() && update.getMessage().getSticker() != null;
    }

    public void kickUser(long chat_id, User userToKick) {
        Integer user_id = userToKick.getId();
        try {
            KickChatMember kickChatMember = new KickChatMember()
                    .setChatId(chat_id)
                    .setUserId(user_id);

            kickMember(kickChatMember);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // necessary to make bot run
    @Override
    public String getBotUsername() {
        return "bittle_bot";
    }

    // necessary to make bot run
    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "419911698:AAG-6x2E2OrRfexMX3qtvFn-iAZcWrqR_c0";
    }
}