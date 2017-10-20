package com.bittle.telegram.main;

import com.bittle.telegram.config.GlobalBotConfig;
import com.bittle.telegram.config.PerGroupBotConfig;
import com.bittle.telegram.Directory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by oscartorres on 6/21/17.
 */
public class MainClass {

    public static final Directory directory = new Directory();
    public static final GlobalBotConfig GLOBAL_BOT_CONFIG = new GlobalBotConfig();
    public static PerGroupBotConfig perGroupBotConfig;

    public void startBot(){

        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        System.out.println("Bot starting up...");
        try {
            botsApi.registerBot(new MainBotClass(new BotMaintainer()));
            System.out.println("Bot startup successful.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Bot startup failed.");
        }

    }

    public static void main(String[] args) {
        new MainClass().startBot();
    }

}
