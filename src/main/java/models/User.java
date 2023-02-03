package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private ArrayList<Post> posts;
    private ArrayList<Integer> followersIDs;

    public User(int id, String name, ArrayList<Post> posts, ArrayList<Integer> followers) {
        this.id = id;
        this.name = name;
        this.posts = posts;
        this.followersIDs = followers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public List<Integer> getFollowers() {
        return followersIDs;
    }

    public void setFollowers(ArrayList<Integer> followers) {
        this.followersIDs = followers;
    }
}

