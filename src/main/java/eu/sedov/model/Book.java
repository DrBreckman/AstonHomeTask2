package eu.sedov.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private final Integer id;
    private String name;
    private String author;
    private final List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }

    public Book(Integer id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
}
