package helpers.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//SingletonSaver
public class FileSaver {
    private static FileSaver saver;

    private FileSaver() {
    }

    public static FileSaver getInstance() {
        if (saver == null)
            saver = new FileSaver();
        return saver;
    }

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
            try {
                FileWriter file = new FileWriter(fileName + ".json");
                file.write(input);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid file type. Must be 'text' or 'json'.");
        }
    }

    public String getPath(File tFile) {
        return tFile.getParentFile().getAbsolutePath() + "/";
    }

}
