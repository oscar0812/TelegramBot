import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MainClass {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            System.out.println("Starting up bot...");
            botsApi.registerBot(new BittleBot());
            System.out.println("Bot started up successfully.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Bot failed to start.");
        }
    }
}
