package org.example.CoffeeBewerter.service;

import  org.example.CoffeeBewerter.Config.ConfigBot;


import lombok.extern.slf4j.Slf4j;
import org.example.CoffeeBewerter.model.CafeRepository;
import org.example.CoffeeBewerter.model.CafeReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.*;
import java.util.*;

@Slf4j
@Component
@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private CafeRepository cafeRepository;
    final ConfigBot config;

    static final String HELP_TEXT = "Этот бот помогает сохранять отзывы о различных кафе.\n\n"+
            "Нажми на кнопку слева от ввода, чтобы выбрать команду.\n\n" +
            "Команда /grade, чтобы оставить оценку на кафе.\n" +
            "Команда /review, для того чтобы увидеть оценку на кафе.\n"+
            "Команда /deletereview, чтобы удалить оценку на кафе";

    public TelegramBot(ConfigBot config){
        this.config = config;
        List <BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Запустить бота"));
        listOfCommands.add(new BotCommand("/grade", "Добавить оценку кафе"));
        listOfCommands.add(new BotCommand("/review", "Посмотреть оценку кафе"));
        listOfCommands.add(new BotCommand("/deletereview", "Удалить оценку кафе"));
        listOfCommands.add(new BotCommand("/help", "Информация как пользоваться ботом"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String TextMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (TextMessage){
                case "/start":
                    startBot(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/grade":
                    SendMessage mesCafe = new SendMessage();
                    mesCafe.setText("Напиши название кофейни, оценку от 1 до 5 и комментарий (через пробел)");
                    registerReview(update.getMessage(), String.valueOf(mesCafe));
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                default: sendMessage(chatId, "К сожалению, сейчас это не поддерживается");

            }
        }
    }

    private void registerReview(Message message, String msg){
        var charId= message.getChatId();
        var chat = message.getChat();

        CafeReview cafeReview = new CafeReview();

        cafeReview.setIdUser(Math.toIntExact(charId));
        cafeReview.setUserName(chat.getUserName());

        String[] strings = msg.split(" ");
        cafeReview.setNameCafeReview(strings[0]);
        cafeReview.setGradeCafeReview(Integer.valueOf(strings[1]));
        cafeReview.setCommentsCafeReview(strings[2]);

        cafeReview.setAddReview(new Timestamp(System.currentTimeMillis()));
        cafeRepository.save(cafeReview);
        log.info("Review saved");
    }

    private void startBot(long chatId, String nameUser){
        String answer = "Привет, " + nameUser + ". Введи команду /grade или /review";
        log.info("Replied to user" + nameUser);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e){
            log.error("Error occured" + e.getMessage());
        }

    }
}
