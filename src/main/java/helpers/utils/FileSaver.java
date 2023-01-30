package helpers.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    public void outputToFile(String input, String fileName, FILE_TYPE fileType) {
        if (fileType == FILE_TYPE.text) {
            try {
                FileWriter writer = new FileWriter(fileName + ".txt");
                writer.write(input);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (fileType == FILE_TYPE.json) {
            String json = "{\"text\": \"" + input + "\"}";
            try {
                FileWriter file = new FileWriter(fileName + ".json");
                file.write(json);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid file type. Must be 'text' or 'json'.");
        }
    }
}
