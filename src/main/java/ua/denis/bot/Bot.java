package ua.denis.bot;


import com.pengrad.telegrambot.TelegramBot;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.denis.bot.listeners.MyListener;

@Component
@NoArgsConstructor
public class Bot {

    Logger logger = LoggerFactory.getLogger(Bot.class);

    TelegramBot telegramBot = null;

    @Value("${bot.token}")
    private String token;

    @Bean
    private void createBot(){
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(new MyListener());
        logger.info("Bot is successfully run");
    }
}
