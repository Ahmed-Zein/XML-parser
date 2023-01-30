package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class XmlConsistencyChecker {
    private File xmlFile;

    private XmlConsistencyChecker() {
    }
    public XmlConsistencyChecker(File f){
        xmlFile = f;
    }

    public boolean checkConsistency() {
        try {
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                xmlContent.append(line);
            }
            bufferedReader.close();

            int bracketCounter = 0;
            for (int i = 0; i < xmlContent.length(); i++) {
                if (xmlContent.charAt(i) == '<') {
                    bracketCounter++;
                } else if (xmlContent.charAt(i) == '>') {
                    bracketCounter--;
                }
                if (bracketCounter < 0) {
                    return false;
                }
            }
            return bracketCounter == 0;
        } catch (IOException e) {
            return false;
        }
    }
}
