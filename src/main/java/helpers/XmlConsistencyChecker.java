package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class XmlConsistencyChecker {
    private File xmlFile;

    private XmlConsistencyChecker() {
    }

    public XmlConsistencyChecker(File f) {
        xmlFile = f;
    }

    public String loadFile() {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(xmlFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return fileContent.toString();
    }


    public boolean checkConsistency() {
        String xml = loadFile();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < xml.length(); i++) {
            if (xml.charAt(i) == '<') {
                int j = i + 1;
                while (j < xml.length() && xml.charAt(j) != '>') {
                    j++;
                }

                if (j == xml.length()) {
                    return false;
                }

                String tag = xml.substring(i + 1, j).trim();
                if (!tag.startsWith("/")) {
                    stack.push(tag);
                } else {
                    if (stack.isEmpty() || !stack.pop().equals(tag.substring(1))) {
                        return false;
                    }
                }

                i = j;
            }
        }

        return stack.isEmpty();
    }

}
