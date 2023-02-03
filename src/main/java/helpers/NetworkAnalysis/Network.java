package helpers.NetworkAnalysis;

import models.Post;
import models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Network {
    private final File xmlFile;
    private final ArrayList<User> users;
    ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

    public Network(File xmlFile) {
        this.xmlFile = xmlFile;
        users = new ArrayList<>(100);
    }

    private void loadUsers() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nList = doc.getElementsByTagName("user");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();

                    NodeList postsList = eElement.getElementsByTagName("post");
                    ArrayList<Post> userPosts = new ArrayList<>();
                    for (int i = 0; i < postsList.getLength(); i++) {
                        Node postNode = postsList.item(i);
                        Element postElement = (Element) postNode;

                        String postBody = postElement.getElementsByTagName("body").item(0).getTextContent();
                        ArrayList<String> postTopics = new ArrayList<>();
                        NodeList topicsList = postElement.getElementsByTagName("topic");
                        for (int j = 0; j < topicsList.getLength(); j++) {

                            postTopics.add(topicsList.item(j).getTextContent());
                        }
                        userPosts.add(new Post(postBody, postTopics));
                    }

                    NodeList followersList = eElement.getElementsByTagName("follower");
                    ArrayList<Integer> followers = new ArrayList<>();
                    for (int i = 0; i < followersList.getLength(); i++) {
                        Node followerNode = followersList.item(i);
                        Element followerElement = (Element) followerNode;
                        followers.add(Integer.valueOf(followerElement.getElementsByTagName("id").item(0).getTextContent()));
                    }
                    users.add(new User(id, name, userPosts, followers));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildAdjacencyList() {
        loadUsers();
        User user;
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            edges.add(new ArrayList<>());
            int itr = users.size() - 1;
            while (itr-- >= 0) {
                edges.get(i).add(0);
            }

            for (int j = 0; j < user.getFollowers().size(); j++) {
                edges.get(i).set(user.getFollowers().get(j) - 1, user.getFollowers().get(j));
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/ahmed/Desktop/outputs/sample.xml");
        Network net = new Network(file);
        net.buildAdjacencyList();
        for (int i = 0; i < net.edges.size(); i++) {
            ArrayList<Integer> row = net.edges.get(i);
            for (Integer integer : row) {
                System.out.print("[ " + integer + " ]" + "\t");
            }
            System.out.println();
        }
    }
}
