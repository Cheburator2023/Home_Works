package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private Map<Long, Message> messageMap = new HashMap<>(150);

    @Override
    public void onUpdated(Message msg) {
        if (!messageMap.containsKey(msg.getId())) {
            messageMap.put(msg.getId(), msg);
        } else {
            if (!messageMap.containsKey(msg.getId())) {
                messageMap.put((msg.getId() +1L), msg);
            } else {
                throw new RuntimeException("Map is full");
            }
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
    }
}
