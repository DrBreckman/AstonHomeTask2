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

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(Integer id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book book))
            return false;

        if (!this.id.equals(book.id))
            return false;

        if (!this.name.equals(book.name))
            return false;

        return this.author.equals(book.author);
    }
}
