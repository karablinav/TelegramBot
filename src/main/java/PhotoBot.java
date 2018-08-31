import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoBot extends TelegramLongPollingBot {
    private final static String START_COMMAND = "/start";
    private final static String PIC_COMMAND = "/pic";
    private final static String MARKUP_COMMAND = "/markup";
    private final static String HIDE_COMMAND = "/hide";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String msgTxt = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (START_COMMAND.equals(msgTxt)) {
                Chat chat = update.getMessage().getChat();
                String firstName = chat.getFirstName();
                String welcomeMsg = String.format("Welcome, %s!", firstName);
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText(welcomeMsg);

                log(firstName, chat.getLastName(), Long.toString(chat.getId()), msgTxt, welcomeMsg);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (PIC_COMMAND.equals(msgTxt)) {
                SendPhoto message = new SendPhoto()
                        .setChatId(chatId)
                        .setPhoto("AgADAgADsqkxG8HlUEhMvpKizdBgawe3tw4ABHui_IVsWh0dmeUCAAEC")
                        .setCaption("Photo");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (MARKUP_COMMAND.equals(msgTxt)) {
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText("Here is your keyboard");
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                KeyboardRow row = new KeyboardRow() {{
                    add(PIC_COMMAND);
                    add(MARKUP_COMMAND);
                    add(HIDE_COMMAND);
                }};
                List<KeyboardRow> keyboard = new ArrayList<>();
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (HIDE_COMMAND.equals(msgTxt)) {
                SendMessage msg = new SendMessage()
                        .setChatId(chatId)
                        .setText("Keyboard hidden");
                ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                msg.setReplyMarkup(keyboardMarkup);
                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage message = new SendMessage().setChatId(chatId).setText("Unknown command");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
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
