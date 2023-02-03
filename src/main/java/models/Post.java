package models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String body;
    private List<String> topics;

    public Post(String body, ArrayList<String> topics) {
        this.body = body;
        this.topics = topics;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
