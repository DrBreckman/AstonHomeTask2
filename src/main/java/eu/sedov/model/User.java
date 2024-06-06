package eu.sedov.model;

import java.util.ArrayList;
import java.util.List;

public final class User {
    private final Integer id;
    private String name;
    private Integer age;
    private String address;
    private List<Review> reviewList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getAge(){
        return age;
    }
    public void setAge(Integer age){
        this.age = age;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public User(Integer id, String name, Integer age, String address){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User user))
            return false;

        if (!this.id.equals(user.id))
            return false;

        if (!this.name.equals(user.name))
            return false;

        if (!this.age.equals(user.age))
            return false;

        return this.address.equals(user.address);
    }
}
