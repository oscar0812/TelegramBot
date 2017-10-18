package com.bittle.bot;

import com.bittle.urban.UrbanDictionary;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BittleBot extends TelegramLongPollingBot {
    private UrbanDictionary dictionary = new UrbanDictionary();
    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.startsWith("/ud ")){
                String lookup = message_text.substring(message_text.indexOf(" ")).trim();
                if(!lookup.isEmpty()){
                    if(dictionary.searchWord(lookup)){
                        sendTxtMsg(chat_id, dictionary.getRandomDefinition().getDefinition());
                    }
                }
                else{
                    sendTxtMsg(chat_id, "No definition found");
                }
            }
        }

    }

    private void sendTxtMsg(long chat_id, String msg){
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(msg);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return Constants.bot_username;
    }

    @Override
    public String getBotToken() {
        // TODO
        return Constants.bot_token;
    }
}
