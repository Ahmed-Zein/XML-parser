package helpers;

import java.io.*;
import java.util.Stack;

public class XMLFixer {
    private Stack<String> tagStack;
    private final File inputFile;
    private final File outputFile;

    public File getOutputFile() {
        return outputFile;
    }

    public XMLFixer(File inputFile) {
        tagStack = new Stack<>();
        this.inputFile = inputFile;
        outputFile = new File(inputFile.getParentFile().getAbsolutePath() + "/" + "corrected.txt");
    }

    public void fixXML() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = fixLine(line);
                if (line.contains("<>")) continue;
                bw.write(line);
                bw.newLine();
            }
        }
    }

    private String fixLine(String line) {
        StringBuilder fixedLine = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '<') {
                int j = i + 1;
                while (j < line.length() && line.charAt(j) != '>') {
                    j++;
                }
                if (j == line.length()) {
                    fixedLine.append(line.substring(i));
                    break;
                }
                String tag = line.substring(i + 1, j).trim();
                if (tag.length() > 0 && !tag.startsWith("/")) {
                    tagStack.push(tag);
                } else if (tag.startsWith("/")) {
                    if (tagStack.isEmpty() || !tagStack.pop().equals(tag.substring(1))) {
                        tag = "";
                    }
                }
                fixedLine.append("<").append(tag).append(">");
                i = j;
            } else {
                fixedLine.append(c);
            }
        }
        while (!tagStack.isEmpty()) {
            fixedLine.append("</").append(tagStack.pop()).append(">");
        }

        return fixedLine.toString();
    }
}