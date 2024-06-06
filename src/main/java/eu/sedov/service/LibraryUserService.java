package eu.sedov.service;

import eu.sedov.model.User;

public interface LibraryUserService {
    User getById(Integer id);
    int insert(User user);
    int delete(Integer id);
    int update(User user);
}
