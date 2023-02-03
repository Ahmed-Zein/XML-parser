package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class XmlMinifier {
    File tFile;

    private XmlMinifier() {
    }

    public XmlMinifier(File f) {
        this.tFile = f;
    }

    public String minifyXml() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(tFile));
        StringBuilder sb = new StringBuilder();

        boolean inTag = false;
        int currentChar;
        while ((currentChar = reader.read()) != -1) {
            char c = (char) currentChar;
            if (c == '<') {
                inTag = true;
                sb.append(c);
            } else if (c == '>') {
                inTag = false;
                sb.append(c);
            } else if (!inTag) {
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        reader.close();
        return sb.toString();

    }

}
