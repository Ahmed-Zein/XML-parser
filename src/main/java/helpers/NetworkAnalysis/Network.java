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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private final File xmlFile;
    private final HashMap<Integer, User> users;
    ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>();

    public Network(File xmlFile) {
        this.xmlFile = xmlFile;
        users = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            adjacencyMatrix.add(new ArrayList<>(Collections.nCopies(users.size(), 0)));
        }

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
                    users.put(id, new User(id, name, userPosts, followers));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildAdjacencyList() {
        loadUsers();
        User user;
        int i = 0;
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            user = entry.getValue();
            adjacencyMatrix.add(new ArrayList<>());
            int itr = users.size() - 1;
            while (itr-- >= 0) {
                adjacencyMatrix.get(i).add(0);
            }
            if (user == null) continue;
            for (int j = 0; j < user.getFollowers().size(); j++) {
                adjacencyMatrix.get(i).set(user.getFollowers().get(j) - 1, 1);
            }
            i++;
        }
    }


    public User getMostInference() { //has the most followers
        int maxFollowers = 0;
        User userWithMostFollowers = null;

        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            int followers = entry.getValue().getFollowers().size();
            if (followers > maxFollowers) {
                maxFollowers = followers;
                userWithMostFollowers = entry.getValue();
            }
        }
        return userWithMostFollowers;
    }

    public User getMostActive() {
        ArrayList<Integer> freq = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {//initialize the array with zero
            freq.add(0);
        }
        for (int i = 0; i < users.size(); i++) {
            ArrayList<Integer> row = adjacencyMatrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                freq.set(j, freq.get(j) + row.get(j));
            }
        }
        System.out.println(freq);
        int maxVal = Integer.MIN_VALUE;
        int idx = 1;
        for (int i = 0; i < freq.size(); i++) {
            if (freq.get(i) > maxVal) idx = i;
        }
        return users.get(idx);
    }


    public static void main(String[] args) {
        File file = new File("C:/Users/ahmed/Desktop/outputs/sample.xml");
        Network net = new Network(file);
        net.buildAdjacencyList();
        for (int i = 0; i < net.adjacencyMatrix.size(); i++) {
            ArrayList<Integer> row = net.adjacencyMatrix.get(i);
            for (Integer integer : row) {
                System.out.print("[ " + integer + " ]" + "\t");
            }
            System.out.println();

        }
        System.out.println(net.users.get(1).getFollowers());
        System.out.println(net.users.get(2).getFollowers());
        System.out.println(net.users.get(3).getFollowers());
        System.out.println(net.users.get(4).getFollowers());
        System.out.println(net.users.get(5).getFollowers());

        System.out.println("who has many followers" + net.getMostInference().getName());
        System.out.println("most Active" + net.getMostActive().getId() + net.getMostActive().getName());
    }
}
