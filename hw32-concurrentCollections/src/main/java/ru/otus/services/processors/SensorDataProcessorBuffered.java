package ru.otus.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final ArrayBlockingQueue<SensorData> buffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.buffer = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        if (!buffer.offer(data)) {
            flush();
            boolean added = buffer.offer(data);
            if (!added) {
                log.error("Не удалось добавить данные датчика после flush: {}", data);
            }
        }
    }

    public void flush() {
        try {
            List<SensorData> dataToWrite = new ArrayList<>();
            buffer.drainTo(dataToWrite);

            dataToWrite.sort(Comparator.comparing(SensorData::getMeasurementTime));

            if (!dataToWrite.isEmpty()) {
                writer.writeBufferedData(dataToWrite);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}