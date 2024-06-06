package eu.sedov.model;

public class Review {

    private final int id;
    private int mark;
    private String description;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Review(int id, int mark, String description) {
        this.id = id;
        this.mark = mark;
        this.description = description;
    }
}
