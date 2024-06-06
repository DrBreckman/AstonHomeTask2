package eu.sedov.service;

import eu.sedov.model.User;

public interface UserService {

    User getById(int id);
    int insert(User user);

}
