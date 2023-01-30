package helpers.LZW;

import helpers.utils.FILE_TYPE;
import helpers.utils.FileSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class LZW {
    private File tFile;
    String dataString = "";

    private LZW() {
    }

    public LZW(File f) {
        this.tFile = f;
        readFromFile();

    }

    protected void checkFileIsNull() throws NullPointerException {
        if (tFile == null) {
            throw new NullPointerException();
        }
    }

    private void readFromFile() {
        try {
            Scanner scanner = new Scanner(tFile);
            while (scanner.hasNextLine()) {
                dataString += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        return tFile.getParentFile().getAbsolutePath() + "/";
    }

    protected void outputToFile(String input, String fileName, FILE_TYPE fileType) {
        new FileSaver().outputToFile(input,getPath() +  fileName, fileType);
    }
}

