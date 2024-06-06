package eu.sedov.service.impl;

import eu.sedov.model.User;
import eu.sedov.repository.EntityRepository;
import eu.sedov.repository.UserRepository;
import eu.sedov.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Override
    public User getById(int id) {
        return repository.get(id);
    }

    @Override
    public int insert(User user) {
        return repository.insert(user);
    }
}
