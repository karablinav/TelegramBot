import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RepeatingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String msgTxt = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(msgTxt);
            try{
                execute(message);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "karablin_bot";
    }

    @Override
    public String getBotToken() {
        return "662834943:AAGdthUOvgNZPBkXjIaVyT2YGobjiimiq9w";
    }
}