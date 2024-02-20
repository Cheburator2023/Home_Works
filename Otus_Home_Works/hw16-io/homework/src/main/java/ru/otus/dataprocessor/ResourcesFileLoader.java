package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (var jsonReader =
                     (ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            Gson gson = new Gson();
            return gson.fromJson(new InputStreamReader(jsonReader, StandardCharsets.UTF_8),
                    new TypeToken<List<Measurement>>() {
                    }.getType());
        } catch (IOException e) {
            logger.error("Ошибка при загрузке файла: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
