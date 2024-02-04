package ru.otus.dataprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class ProcessorAggregator implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> aggregatedData = new TreeMap<>();

        try {
            Gson gson = new Gson();
            String jsonData = gson.toJson(data);

            List<Measurement> measurements = gson.fromJson(jsonData, new TypeToken<List<Measurement>>() {
            }.getType());

            for (Measurement measurement : measurements) {
                String name = measurement.name();
                double value = measurement.value();

                aggregatedData.merge(name, value, Double::sum);
            }
        } catch (FileProcessException e) {
            logger.error("Ошибка при обработке данных: {}", e.getMessage());
            return Collections.emptyMap();
        }

        return aggregatedData;
    }
}
