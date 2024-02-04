package ru.otus.dataprocessor;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);

    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            Gson gson = new Gson();
            String jsonData = gson.toJson(data);


            fileWriter.write(jsonData);
        } catch (IOException e) {
            logger.error("Ошибка при обработке данных: {}", e.getMessage());
        }
    }
}
