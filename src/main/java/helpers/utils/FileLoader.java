package helpers.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Singleton class
public class FileLoader {
    private static FileLoader instance;
    private List<String> lines;

    private FileLoader() {
        lines = new ArrayList<>();
    }

    public static FileLoader getInstance() {
        if (instance == null) {
            instance = new FileLoader();
        }
        return instance;
    }

    public List<String> loadFile(String filePath) throws IOException {
        lines.clear();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}
