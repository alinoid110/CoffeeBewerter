package org.example.CoffeeBewerter.Config;

import org.example.CoffeeBewerter.service.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.net.SocketOption;
import java.sql.SQLOutput;

@Slf4j
@Component
@AllArgsConstructor
public class BotInitializer {
    @Autowired
    TelegramBot bot;
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e) {
            System.out.println("Error BotInitializer");
            log.error("Error occured" + e.getMessage());
        }
    }
}
