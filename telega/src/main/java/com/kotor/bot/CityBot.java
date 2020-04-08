package com.kotor.bot;

import com.kotor.config.BotConfig;
import com.kotor.request.SendRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InvalidObjectException;
import java.util.concurrent.ConcurrentHashMap;

public class CityBot extends TelegramLongPollingBot {

    private BotConfig botConfig = BotConfig.getInstance();

    private static final int WAIT_FOR_COMMAND = -1;
    private static final int CREATE_NEW_CITY_NAME = 1;
    private static final int CREATE_NEW_CITY_CONTENT = 2;
    private static final int FIND_BY_NAME = 3;

    private static final String CANCEL_COMMAND = "/stop";
    private static final String ADD_CITY_COMMAND = "/addCity";
    private static final String FIND_CITY_COMMAND = "/findByName";

    private final ConcurrentHashMap<Integer, Integer> userState = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> userCity = new ConcurrentHashMap<>();

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                try {
                    handleIncomingMessage(message);
                } catch (InvalidObjectException e) {
                }
            }
        } catch (Exception e) {
        }
    }

    private void handleIncomingMessage(Message message) throws InvalidObjectException {
        int state = userState.getOrDefault(message.getFrom().getId(), 0);
        System.out.println(state);
        switch (state) {
            case WAIT_FOR_COMMAND:
                checkoutForCommand(message);
                break;
            case FIND_BY_NAME:
                findByName(message);
                break;
            case CREATE_NEW_CITY_NAME:
                createCityName(message);
                break;
            case CREATE_NEW_CITY_CONTENT:
                createCityContent(message);
                break;
            default:
                sendHelpMessage(message.getChatId(), message.getMessageId(), null);
                userState.put(message.getFrom().getId(), WAIT_FOR_COMMAND);
                break;
        }
    }

    private void checkoutForCommand(Message message) {
        String value = message.getText();
        switch (value){
            case ADD_CITY_COMMAND:
                userState.put(message.getFrom().getId(), CREATE_NEW_CITY_NAME);
                sendMessage("Enter new name of a city", message);
                break;
            case FIND_CITY_COMMAND:
                userState.put(message.getFrom().getId(), FIND_BY_NAME);
                sendMessage("Enter the name of city", message);
                break;
            default:
                userState.put(message.getFrom().getId(), WAIT_FOR_COMMAND);
                sendErrorMessage(message, value);
        }
    }

    private void createCityContent(Message message) {
        String content = message.getText();
        String name = userCity.get(message.getFrom().getId());
        String result = SendRequest.createCity(name, content);
        sendMessage(result, message);
        userCity.remove(message.getFrom().getId());
        userState.remove(message.getFrom().getId());
    }

    private void createCityName(Message message) {
        String name = message.getText();
        userCity.put(message.getFrom().getId(), name);
        sendMessage("Enter description", message);
        userState.remove(message.getFrom().getId());
        userState.put(message.getFrom().getId(), CREATE_NEW_CITY_CONTENT);
    }

    private void findByName(Message message) {
        String name = message.getText();
        String result = SendRequest.findCity(name);
        sendMessage(result, message);
        userState.remove(message.getFrom().getId());
    }

    private void sendMessage(String result, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(result);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(message.getMessageId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            sendErrorMessage(message, e.getMessage());
        }
    }

    private void sendErrorMessage(Message message, String errorText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(String.format("ERROR_MESSAGE_TEXT", message.getText().trim(), errorText.replace("\"", "\\\"")));
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }
    }


    private void sendHelpMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText("What would you like to do?" + "\n" +
                ADD_CITY_COMMAND + "\n" +
                FIND_CITY_COMMAND + "\n");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }
    }
}
