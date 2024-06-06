package eu.sedov.model;

public class Review {
    private final Integer id;
    private Integer mark;
    private String description;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Integer getId() {
        return id;
    }
    public Integer getMark() {
        return mark;
    }
    public String getDescription() {
        return description;
    }

    public Review(Integer id, Integer mark, String description) {
        this.id = id;
        this.mark = mark;
        this.description = description;
    }
}
