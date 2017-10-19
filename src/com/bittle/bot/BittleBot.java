package com.bittle.bot;

import com.bittle.bot.util.StringUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BittleBot extends TelegramLongPollingBot {
    private final int MAX_MESSAGE_LENGTH = 4096;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chat_id = update.getMessage().getChatId();
            if(message.hasText()) {
                String message_text = update.getMessage().getText();
                String botResponse = StringUtil.getResponse(message_text);
                if(!botResponse.isEmpty()){
                    System.out.println(botResponse.length());

                    if(botResponse.length() > MAX_MESSAGE_LENGTH){
                        if(message.isGroupMessage())
                            sendTxtMsg(chat_id, "Message too long, sending to pm", message.getMessageId());
                        int num_msg = (int)(Math.ceil(botResponse.length()/ (double)MAX_MESSAGE_LENGTH));

                        for(int x =0; x<num_msg; x++){
                            String current = botResponse;
                            if(botResponse.length() > MAX_MESSAGE_LENGTH) {
                                current = botResponse.substring(0, MAX_MESSAGE_LENGTH);
                                botResponse = botResponse.substring(MAX_MESSAGE_LENGTH);
                            }
                            sendTxtMsg(message.getFrom().getId(), current, message.getMessageId());
                        }
                    }
                    else
                        sendTxtMsg(chat_id, botResponse, message.getMessageId());
                }
            }
            log(chat_id, message);
        }

    }

    private void sendTxtMsg(long chat_id, String message_text, Integer message_id){
        SendMessage sendMessage = new SendMessage()
                .setChatId(chat_id)
                .setText(message_text)
                .setReplyToMessageId(message_id);
        try {
            execute(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void log(long chat_id, Message message){
        System.out.println("\n["+chat_id+"] => "+message.getFrom().getUserName());
        if(message.hasText()){
            System.out.println("[incoming text] => "+message.getText());
        }
        else if(message.hasPhoto()){
            for(PhotoSize p: message.getPhoto()) {
                System.out.println("[incoming photo] => (W = "+p.getWidth()+", H = "+p.getHeight()+"), " +
                        "size = " +p.getFileSize()+", ID = "+p.getFileId()+", path = "+p.getFilePath());
            }
        }

        System.out.println("");
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
