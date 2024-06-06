package eu.sedov.model;

import java.util.ArrayList;
import java.util.List;

public final class User {
    private final int id;
    private String name;
    private int age;
    private String address;
    private final List<Review> reviewList = new ArrayList<>();
    private final List<Book> bookList = new ArrayList<>();

    public List<Review> getReviewList() {
        return reviewList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public User(int id, String name, int age, String address){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User user))
            return false;

        if (this.id != user.id)
            return false;

        if (!this.name.equals(user.name))
            return false;

        if (this.age != user.age)
            return false;

        return this.address.equals(user.address);
    }
}
