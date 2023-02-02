package helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class XmlFileChecker {
    private List<String> lines;
    private List<String> errors;
    private File tFile;

    private XmlFileChecker() {
    }

    public XmlFileChecker(File f) {
        this.tFile = f;
    }

    public String checkAndCorrect() throws IOException {
        // Load the XML file
        loadFile();

        // Check for XML syntax errors
        checkForErrors();

        // Correct the errors
        correctErrors();

        // Write the corrected XML back to the file
        writeFile();
        return lines.toString();
    }

    private void loadFile() throws IOException {
        lines = new ArrayList<>();
        errors = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(tFile));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

    }

    private void checkForErrors() {
        Stack<String> tags = new Stack<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '<') {
                    int endIndex = line.indexOf('>', j + 1);
                    if (endIndex == -1) {
                        // Unclosed tag
                        errors.add("Line " + (i + 1) + ": Unclosed tag");
                        break;
                    }
                    String tag = line.substring(j + 1, endIndex);
                    if (!tag.startsWith("/")) {
                        // Opening tag
                        tags.push(tag);
                    } else {
                        // Closing tag
                        String openingTag = tags.pop();
                        if (!tag.substring(1).equals(openingTag)) {
                            // Mismatched tag
                            errors.add("Line " + (i + 1) + ": Mismatched tag, expected </" + openingTag + "> but got " + tag);
                        }
                    }
                }
            }
        }
        if (!tags.isEmpty()) {
            // Missing closing tags
            errors.add("Missing closing tag(s) for " + tags);
        }
    }

    private void correctErrors() {
        for (String error : errors) {
            System.out.println(error);
            if (error.contains("Unclosed tag")) {
                int lineNumber = Integer.parseInt(error.substring(6, error.indexOf(":")));
                String line = lines.get(lineNumber - 1);
                int tagStart = line.indexOf("<");
                int tagEnd = line.lastIndexOf(">");
                if (tagEnd < tagStart) {
                    // The tag has not been closed in the same line.
                    // Try to find the closing tag in the following lines.
                    int closingTagLineNumber = -1;
                    for (int i = lineNumber; i < lines.size(); i++) {
                        line = lines.get(i);
                        tagEnd = line.indexOf(">");
                        if (tagEnd > -1) {
                            closingTagLineNumber = i;
                            break;
                        }
                    }
                    if (closingTagLineNumber == -1) {
                        // The closing tag could not be found, so add it to the end of the file.
                        lines.add("</" + line.substring(tagStart + 1) + ">");
                    } else {
                        // The closing tag was found in a later line.
                        // Add the missing part of the opening tag to that line.
                        String closingTagLine = lines.get(closingTagLineNumber);
                        lines.set(lineNumber - 1, line.substring(0, tagEnd + 1));
                        lines.set(closingTagLineNumber, "</" + line.substring(tagStart + 1, tagEnd) + ">" + closingTagLine.substring(tagEnd + 1));
                    }
                } else {
                    // The tag has been closed in the same line, but it's not a complete tag.
                    // Add the missing part of the opening tag to the beginning of the next line.
                    if (lineNumber < lines.size()) {
                        lines.set(lineNumber, line.substring(0, tagEnd + 1) + lines.get(lineNumber));
                    } else {
                        lines.add(line.substring(0, tagEnd + 1));
                    }
                }
            }
        }
    }


    private String getPath() {
        return tFile.getParentFile().getAbsolutePath() + "/";
    }

    private void writeFile() throws IOException {
        FileWriter writer = new FileWriter(getPath() + "corrected" + ".txt");
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
    }
}

