package helpers;

import java.io.*;

public class XmlPrettifies {

    private static final String INDENT_STRING = "\t";
    public String loadFile(File xmlFile) {
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

    public String prettify(File xmlFile) throws IOException {
        String xmlInput = loadFile(xmlFile);
        StringWriter output = new StringWriter();
        BufferedReader reader = new BufferedReader(new StringReader(xmlInput));
        BufferedWriter writer = new BufferedWriter(output);
        String line;
        String indent = "";
        boolean inTag = false;
        boolean inComment = false;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("<!--")) {
                inComment = true;
            }
            if (inComment) {
                writer.write(indent);
                writer.write(line);
                writer.newLine();
                if (line.endsWith("-->")) {
                    inComment = false;
                }
                continue;
            }
            if (line.startsWith("<")) {
                if (line.startsWith("</")) {
                    indent = indent.substring(INDENT_STRING.length());
                }
                if (inTag) {
                    writer.newLine();
                }
                writer.write(indent);
                writer.write(line);
                inTag = true;
                if (!line.endsWith("/>")) {
                    indent += INDENT_STRING;
                }
            } else {
                if (inTag) {
                    inTag = false;
                    writer.newLine();
                }
                writer.write(indent);
                writer.write(line);
            }
            writer.newLine();
        }
        writer.flush();
        return output.toString();
    }
}
