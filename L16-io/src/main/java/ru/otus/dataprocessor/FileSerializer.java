package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    Gson gson;

    Path path;

    public FileSerializer(String fileName) {
        this.gson = new Gson();
        this.path = Paths.get(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        var dataMap = new TreeMap<>(data);
        var json = gson.toJson(dataMap);
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
