package ihs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestingUtils {

    public static JsonObject readJsonObject(String fileLocation) {
        try{
            Path file = new File(fileLocation).toPath();
            String content = new String(Files.readAllBytes(file));
            JsonElement root = new JsonParser().parse(content);
            return root.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
