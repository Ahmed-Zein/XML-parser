package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Node {
    String name;
    String value;
    List<Node> children;

    Node(String name, String value) {
        this.name = name;
        this.value = value;
        this.children = new ArrayList<Node>();
    }
}

class Tree {
    Node root;
    FileReader tFile;

    Tree(Node root, String filePath) throws FileNotFoundException {
        this.root = root;
        this.tFile = new FileReader(filePath);
    }

    private String extractText() throws IOException {
        BufferedReader br = new BufferedReader(tFile);
        StringBuilder text = new StringBuilder("");
        String line = br.readLine();
        while (line != null) {
            text.append(line.trim()).append(System.lineSeparator());
            line = br.readLine();
        }
        return new String(text);

    }

    public void buildTree() {
        String xml;
        try {
            xml = extractText();
            Node currentNode = root;
            Stack<Node> stack = new Stack<Node>();
            stack.push(currentNode);
            int i = 0;
            while (i < xml.length()) {
                char c = xml.charAt(i);
                int j = i + 1;
                if (c == '<') {
                    while (xml.charAt(j) != '>') {
                        j++;
                    }
                    String tag = xml.substring(i + 1, j);
                    if (!tag.startsWith("/")) {
                        Node newNode = new Node(tag.trim(), "");
                        currentNode.children.add(newNode);
                        stack.push(newNode);
                        currentNode = newNode;
                    } else {
                        currentNode = stack.pop();
                    }
                    i = j;
                } else {
                    while (j < xml.length() && xml.charAt(j) != '<') {
                        j++;
                    }
                    currentNode.value += xml.substring(i, j).trim();
                    i = j - 1;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void traverseBFS() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            System.out.println("node: " + currentNode.name + "\t value: " + currentNode.value);
            for (Node child : currentNode.children) {
                queue.add(child);
            }
        }
    }

    private void toJsonHelper(StringBuilder json, Node node) {
        json.append("\"").append(node.name.trim()).append("\":");
        if (node.children.isEmpty()) {
            json.append("\"").append(node.value).append("\"");
        } else {
            json.append("{");
            for (int i = 0; i < node.children.size(); i++) {
                toJsonHelper(json, node.children.get(i));
                if (i < node.children.size() - 1) {
                    json.append(",");
                }
            }
            json.append("}");
        }
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        toJsonHelper(json, root);
        json.append("}");
        return json.toString();
    }

    public static String prettifyJson(String json) {
        StringBuilder builder = new StringBuilder();
        int indent = 0;
        boolean inString = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (inString) {
                if (c == '\"') {
                    inString = false;
                }
                builder.append(c);
            } else {
                switch (c) {
                    case '{':
                    case '[':
                        builder.append(c).append("\n");
                        indent++;
                        addIndent(builder, indent);
                        break;
                    case '}':
                    case ']':
                        builder.append("\n");
                        indent--;
                        addIndent(builder, indent);
                        builder.append(c);
                        break;
                    case ',':
                        builder.append(c).append("\n");
                        addIndent(builder, indent);
                        break;
                    case '\"':
                        inString = true;
                        builder.append(c);
                        break;
                    default:
                        builder.append(c);
                        break;
                }
            }
        }
        return builder.toString();
    }

    private static void addIndent(StringBuilder builder, int indent) {
        for (int i = 0; i < indent; i++) {
            builder.append("    ");
        }
    }


    public static void main(String[] args) {
        Node rooti = new Node("", "");
        try {
        Tree treeri = new Tree(rooti,"C://Users//ahmed//Desktop//bang//src//com//company//gg.txt");
        treeri.buildTree();
        System.out.println(prettifyJson(treeri.toJson()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
