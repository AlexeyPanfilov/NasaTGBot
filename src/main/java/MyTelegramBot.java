import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    public final static String BOT_TOKEN = "5688770042:AAHHiyFBlgg72vvP-lVm4pOQhWvhVTJ4OEc";
    public final static String BOT_USERNAME = "APNetologyNasaPic_Bot";
    public final static String URI = "https://api.nasa.gov/planetary/apod?api_key=5gUp38zK78e8gDBK7dlJGlgzejcSPubyjC4xiVFC";
    public static long chatId;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот NASA. Я высылаю картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются каждые сутки");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    sendMessage("Моя твоя не понимать. Отправь /help или /give, " +
                            "всё просто, не надо пока усложнять :-)");
                    break;
            }
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
