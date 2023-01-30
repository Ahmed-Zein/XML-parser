package helpers.LZW;

import helpers.utils.FILE_TYPE;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Decompressor extends LZW {
    public Decompressor(File tFile) {
        super(tFile);
    }

    public String deCompress() {
        checkFileIsNull();

        HashMap<Integer, String> dictionary = new LinkedHashMap<>();
        String[] data = (dataString + "").split("");
        String currentChar = data[0];
        String oldPhrase = currentChar;
        String out = currentChar;
        int code = 256;
        String phrase = "";
        for (int i = 1; i < data.length; i++) {
            int currCode = Character.codePointAt(data[i], 0);
            if (currCode < 256) {
                phrase = data[i];
            } else {
                if (dictionary.get(currCode) != null) {
                    phrase = dictionary.get(currCode);
                } else {
                    phrase = oldPhrase + currentChar;
                }
            }
            out += phrase;
            currentChar = phrase.substring(0, 1);
            dictionary.put(code, oldPhrase + currentChar);
            code++;
            oldPhrase = phrase;
        }
        outputToFile(out, "DecompressedFile", FILE_TYPE.text);
        return out;
    }
}
