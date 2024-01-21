package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new HashMap<>();

    public void onUpdated(Message message) {
        Message copy = message.clone();
        history.put(copy.getId(), copy);
    }

    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id)).map(Message::clone);
    }
}
