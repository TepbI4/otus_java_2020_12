package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoader implements Loader {

    private final List<Measurement> measurements;

    private static final Type LIST_MEASUREMENT_TYPE = new TypeToken<List<Measurement>>(){}.getType();

    public FileLoader(String fileName) {
        try {
            var resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new FileProcessException("File '" + fileName + "' is not found in resources");
            }
            var jsonContent = Files.readString(Path.of(resource.toURI()));
            this.measurements = new Gson().fromJson(jsonContent, LIST_MEASUREMENT_TYPE);
        } catch (URISyntaxException | IOException e) {
            throw new FileProcessException(e);
        }
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        return measurements;
    }
}
