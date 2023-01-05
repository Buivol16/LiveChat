package ua.denis.bot.listeners;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.util.List;

public class MyListener implements UpdatesListener {
    @Override
    public int process(List<Update> list) {
        list.forEach(System.out::println);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
