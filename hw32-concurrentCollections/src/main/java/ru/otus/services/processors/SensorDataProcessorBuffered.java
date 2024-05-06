package ru.otus.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final List<SensorData> buffer = new ArrayList<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        synchronized (buffer) {
            buffer.add(data);
            buffer.sort(Comparator.comparing(SensorData::getMeasurementTime));
            if (buffer.size() >= bufferSize) {
                flush();
            }
        }
    }

    public void flush() {
        synchronized (buffer) {
            try {
                if (!buffer.isEmpty()) {
                    writer.writeBufferedData(new ArrayList<>(buffer));
                    buffer.clear();
                }
            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}