package helpers.LZW;

import helpers.utils.FILE_TYPE;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Compressor extends LZW {
    public Compressor(File tFile){
        super(tFile);
    }
    public String compress() {
        HashMap<String, Integer> dictionary = new LinkedHashMap<>();
        String[] data = (super.dataString + "").split("");
        String out = "";
        ArrayList<String> temp_out = new ArrayList<>();
        String currentChar;
        String phrase = data[0];
        int code = 256;
        for (int i = 1; i < data.length; i++) {
            currentChar = data[i];
            if (dictionary.get(phrase + currentChar) != null) {
                phrase += currentChar;
            } else {
                if (phrase.length() > 1) {
                    temp_out.add(Character.toString((char) dictionary.get(phrase).intValue()));
                } else {
                    temp_out.add(Character.toString((char) Character.codePointAt(phrase, 0)));
                }

                dictionary.put(phrase + currentChar, code);
                code++;
                phrase = currentChar;
            }
        }

        if (phrase.length() > 1) {
            temp_out.add(Character.toString((char) dictionary.get(phrase).intValue()));
        } else {
            temp_out.add(Character.toString((char) Character.codePointAt(phrase, 0)));
        }

        for (String outchar : temp_out) {
            out += outchar;
        }
//        super.outputToFile(out,"compressedFile", FILE_TYPE.text);
        return out;
    }

}
