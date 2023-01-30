//import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
//import java.io.*;
//
//public class XmlPrettifier {
//
//    private static final String INDENT_STRING = "    ";
//
//    public static String prettify(String xmlInput) throws IOException {
//        StringWriter output = new StringWriter();
//        BufferedReader reader = new BufferedReader(new StringReader(xmlInput));
//        BufferedWriter writer = new BufferedWriter(output);
//        String line;
//        String indent = "";
//        boolean inTag = false;
//        boolean inComment = false;
//        while ((line = reader.readLine()) != null) {
//            line = line.trim();
//            if (line.startsWith("<!--")) {
//                inComment = true;
//            }
//            if (inComment) {
//                writer.write(indent);
//                writer.write(line);
//                writer.newLine();
//                if (line.endsWith("-->")) {
//                    inComment = false;
//                }
//                continue;
//            }
//            if (line.startsWith("<")) {
//                if (line.startsWith("</")) {
//                    indent = indent.substring(INDENT_STRING.length());
//                }
//                if (inTag) {
//                    writer.newLine();
//                }
//                writer.write(indent);
//                writer.write(line);
//                inTag = true;
//                if (!line.endsWith("/>")) {
//                    indent += INDENT_STRING;
//                }
//            } else {
//                if (inTag) {
//                    inTag = false;
//                    writer.newLine();
//                }
//                writer.write(indent);
//                writer.write(line);
//            }
//            writer.newLine();
//        }
//        writer.flush();
//        return output.toString();
//    }
//
//    public static void main(String[] args) throws IOException {
//        System.out.println("prettifying");
//        System.out.println(XmlPrettifier.prettify("<users>\n" +
//                "    <user>\n" +
//                "        <id>1</id>\n" +
//                "        <name>Ahmed Ali</name>\n" +
//                "        <posts>\n" +
//                "            <post>\n" +
//                "                <body>\n" +
//                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
//                "                </body>\n" +
//                "                <topics>\n" +
//                "                    <topic>\n" +
//                "                        economy\n" +
//                "                    </topic>\n" +
//                "                    <topic>\n" +
//                "                        finance\n" +
//                "                    </topic>\n" +
//                "                </topics>\n" +
//                "            </post>\n" +
//                "            <post>\n" +
//                "                <body>\n" +
//                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
//                "                </body>\n" +
//                "                <topics>\n" +
//                "                    <topic>\n" +
//                "                        solar_energy\n" +
//                "                    </topic>\n" +
//                "                </topics>\n" +
//                "            </post>\n" +
//                "        </posts>\n" +
//                "        <followers>\n" +
//                "            <follower>\n" +
//                "                <id>2</id>\n" +
//                "            </follower>\n" +
//                "            <follower>\n" +
//                "                <id>3</id>\n" +
//                "            </follower>\n" +
//                "        </followers>\n" +
//                "    </user>\n" +
//                "    <user>\n" +
//                "        <id>2</id>\n" +
//                "        <name>Yasser Ahmed</name>\n" +
//                "        <posts>\n" +
//                "            <post>\n" +
//                "                <body>\n" +
//                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
//                "                </body>\n" +
//                "                <topics>\n" +
//                "                    <topic>\n" +
//                "                        education\n" +
//                "                    </topic>\n" +
//                "                </topics>\n" +
//                "            </post>\n" +
//                "        </posts>\n" +
//                "        <followers>\n" +
//                "            <follower>\n" +
//                "                <id>1</id>\n" +
//                "            </follower>\n" +
//                "        </followers>\n" +
//                "    </user>\n" +
//                "    <user>\n" +
//                "        <id>3</id>\n" +
//                "        <name>Mohamed Sherif</name>\n" +
//                "        <posts>\n" +
//                "            <post>\n" +
//                "                <body>\n" +
//                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
//                "                </body>\n" +
//                "                <topics>\n" +
//                "                    <topic>\n" +
//                "                        sports\n" +
//                "                    </topic>\n" +
//                "                </topics>\n" +
//                "            </post>\n" +
//                "        </posts>\n" +
//                "        <followers>\n" +
//                "            <follower>\n" +
//                "                <id>1</id>\n" +
//                "            </follower>\n" +
//                "        </followers>\n" +
//                "    </user>\n" +
//                "</users>"));
//
//    }
//}
